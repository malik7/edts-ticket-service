package id.co.edts.ticketservice.service.impl;

import id.co.edts.apicore.dto.ResponseDto;
import id.co.edts.apicore.utils.JsonConverterUtil;
import id.co.edts.apicore.utils.ResponseUtil;
import id.co.edts.basedomain.repository.CustomerRepository;
import id.co.edts.basedomain.repository.TTicketOrderRepository;
import id.co.edts.ticketservice.dto.UserTicketReqDto;
import id.co.edts.ticketservice.dto.UserTicketRespDto;
import id.co.edts.ticketservice.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserImpl implements UserService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TTicketOrderRepository tTicketOrderRepository;

    @Override
    public ResponseDto<UserTicketRespDto> getUserTicket(UserTicketReqDto param, HttpServletRequest request) {
        var tticketOrder = tTicketOrderRepository.findByReceiptNo(param.getReceiptNo());
        if (tticketOrder.isEmpty()) {
            return ResponseUtil.failed(new ResponseDto(), String.valueOf(HttpStatus.NOT_FOUND.value()), "Concert Ticket Not Found");
        }

        var result = JsonConverterUtil.fromObject(tticketOrder.get(), UserTicketRespDto.class);

        return ResponseUtil.success(new ResponseDto<>(result));
    }
}
