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
public class ConcertTicketRespDto {
    private Long id;
    private Date purchaseStartDate;
    private Date purchaseEndDate;
    private String description;
    private BigDecimal price;

    @JsonAlias({"masterConcert"})
    private ConcertRespDto concert;
}
