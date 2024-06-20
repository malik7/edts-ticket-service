package id.co.edts.ticketservice.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConcertRespDto {
    private Long id;
    private String name;
    private Date date;

    @JsonAlias({"masterVenue"})
    private VenueRespDto venue;
}
