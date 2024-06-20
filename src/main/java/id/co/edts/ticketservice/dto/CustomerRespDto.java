package id.co.edts.ticketservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRespDto {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
}
