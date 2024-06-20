package id.co.edts.ticketservice.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConcertBookingTicketResDto {
    private CustomerRespDto customer;
    private ConcertTicketRespDto ticket;
    private String receiptNo;
    private Date orderTime;
    private Integer numberOfTicket;
    private BigDecimal totalPrice;
}
