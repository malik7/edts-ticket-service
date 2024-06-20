package id.co.edts.ticketservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VenueRespDto {
    private Long id;
    private String name;
    private String location;
    private String capacity;
}
