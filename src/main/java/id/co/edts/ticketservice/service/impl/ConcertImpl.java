package id.co.edts.ticketservice.service.impl;

import id.co.edts.apicore.dto.PageableResponseDto;
import id.co.edts.apicore.dto.ResponseDto;
import id.co.edts.apicore.query.dto.CriteriaField;
import id.co.edts.apicore.utils.JsonConverterUtil;
import id.co.edts.apicore.utils.ResponseUtil;
import id.co.edts.basedomain.model.MasterCustomer;
import id.co.edts.basedomain.model.TTicketOrder;
import id.co.edts.basedomain.repository.ConcertRepository;
import id.co.edts.basedomain.repository.CustomerRepository;
import id.co.edts.basedomain.repository.TTicketOrderRepository;
import id.co.edts.basedomain.repository.TicketRepository;
import id.co.edts.ticketservice.dto.*;
import id.co.edts.ticketservice.service.ConcertService;
import id.co.edts.ticketservice.utils.SearchCriteriaUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class ConcertImpl implements ConcertService {

    @Autowired
    private ConcertRepository concertRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TTicketOrderRepository tTicketOrderRepository;

    private final Lock lock = new ReentrantLock();

    @Override
    public PageableResponseDto<List<ConcertRespDto>> list(CriteriaField param, HttpServletRequest request) {
        var pageData = concertRepository.findAll(SearchCriteriaUtil.createSpecification(param), param.getPageable());

        var result = JsonConverterUtil.fromlist(pageData.getContent(), ConcertRespDto.class);
        return ResponseUtil.success(new PageableResponseDto<>(pageData, result));
    }

    @Override
    public ResponseDto<ConcertTicketRespDto> getConcertTicket(Long concertId, HttpServletRequest request) {
        var ticket = ticketRepository.findAllByMasterConcertId(concertId);
        if (ticket.isEmpty()) {
            return ResponseUtil.failed(new ResponseDto(), String.valueOf(HttpStatus.NOT_FOUND.value()), "Concert Ticket Not Found");
        }

        var result = JsonConverterUtil.fromlist(ticket.get(), ConcertTicketRespDto.class);

        return ResponseUtil.success(new ResponseDto<>(result));
    }

    @Transactional
    @Override
    public ResponseDto bookingConcertTicket(ConcertBookingTicketReqDto param, HttpServletRequest request) {
        lock.lock();
        try {
            var customer = customerRepository.findByEmailOrPhoneNumber(param.getCustomerEmail(), param.getCustomerPhoneNumber());
            if (customer.isPresent()) {
                var tticketOrder = tTicketOrderRepository.findByMasterCustomerIdAndMasterTicketId(customer.get().getId(), param.getTicketId());
                if (tticketOrder.isPresent()) {
                    return ResponseUtil.failed(new ResponseDto(), String.valueOf(HttpStatus.NOT_FOUND.value()), "Email already booking this concert ticket");
                }
            }

            var ticket = ticketRepository.findById(param.getTicketId());
            if (ticket.isEmpty()) {
                return ResponseUtil.failed(new ResponseDto(), String.valueOf(HttpStatus.NOT_FOUND.value()), "Ticket Not Found");
            }

            var numberLimit = ticket.get().getTicketNumberLimit();
            var numberSold = ticket.get().getTicketNumberSold();
            if (numberSold >= numberLimit) {
                return ResponseUtil.failed(new ResponseDto(), String.valueOf(HttpStatus.BAD_REQUEST.value()), "Ticket have reached the purchase limit during this period");
            }

            numberSold += param.getNumberOfTicket();
            if (numberSold >= numberLimit) {
                return ResponseUtil.failed(new ResponseDto(), String.valueOf(HttpStatus.BAD_REQUEST.value()), "The number of booked ticket exceeds the purchase limit in this period");
            }

            var dateNow = new Date();
            if (dateNow.before(ticket.get().getPurchaseStartDate()) || dateNow.after(ticket.get().getPurchaseEndDate())) {
                return ResponseUtil.failed(new ResponseDto(), String.valueOf(HttpStatus.BAD_REQUEST.value()), "Ticket Not in Purchase Period");
            }

            MasterCustomer masterCustomer = new MasterCustomer();
            masterCustomer.setName(param.getCustomerName());
            masterCustomer.setEmail(param.getCustomerEmail());
            masterCustomer.setPhoneNumber(param.getCustomerPhoneNumber());
            customerRepository.save(masterCustomer);

            var receiptNo = UUID.randomUUID().toString();
            var totalPrice = ticket.get().getPrice().multiply(new BigDecimal(param.getNumberOfTicket()));

            TTicketOrder tTicketOrder = new TTicketOrder();
            tTicketOrder.setMasterCustomer(masterCustomer);
            tTicketOrder.setMasterTicket(ticket.get());
            tTicketOrder.setOrderTime(dateNow);
            tTicketOrder.setNumberOfTicket(param.getNumberOfTicket());
            tTicketOrder.setTotalPrice(totalPrice);
            tTicketOrder.setOrderStatus("SUCCESS");
            tTicketOrder.setReceiptNo(receiptNo);
            tTicketOrderRepository.save(tTicketOrder);

            var mTicket = ticket.get();
            mTicket.setTicketNumberSold(numberSold);
            ticketRepository.save(mTicket);

            var customerRespDto = JsonConverterUtil.fromObject(masterCustomer, CustomerRespDto.class);
            var concertTicketRespDto = JsonConverterUtil.fromObject(ticket.get(), ConcertTicketRespDto.class);
            ConcertBookingTicketResDto resDto = new ConcertBookingTicketResDto();
            resDto.setCustomer(customerRespDto);
            resDto.setTicket(concertTicketRespDto);
            resDto.setReceiptNo(receiptNo);
            resDto.setOrderTime(dateNow);
            resDto.setNumberOfTicket(param.getNumberOfTicket());
            resDto.setTotalPrice(totalPrice);

            return ResponseUtil.success(new ResponseDto<>(resDto));
        } finally {
            lock.unlock();
        }
    }
}
