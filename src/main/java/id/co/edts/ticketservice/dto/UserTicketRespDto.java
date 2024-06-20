package id.co.edts.ticketservice.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTicketRespDto {
    @JsonAlias({"masterTicket"})
    private ConcertTicketRespDto ticket;

    @JsonAlias({"masterCustomer"})
    private CustomerRespDto customer;

    private Date orderTime;
    private Integer numberOfTicket;
    private BigDecimal totalPrice;
    private String orderStatus;
    private String receiptNo;
}
