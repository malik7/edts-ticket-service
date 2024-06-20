package id.co.edts.ticketservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConcertBookingTicketReqDto {
    private Long concertId;
    private Long ticketId;
    private String customerName;
    private String customerEmail;
    private String customerPhoneNumber;
    private Integer numberOfTicket;
    private BigDecimal price;
}
