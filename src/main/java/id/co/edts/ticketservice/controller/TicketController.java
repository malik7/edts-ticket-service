package id.co.edts.ticketservice.controller;

import id.co.edts.apicore.dto.PageableResponseDto;
import id.co.edts.apicore.dto.ResponseDto;
import id.co.edts.apicore.query.dto.CriteriaField;
import id.co.edts.ticketservice.dto.ConcertBookingTicketReqDto;
import id.co.edts.ticketservice.dto.ConcertRespDto;
import id.co.edts.ticketservice.dto.ConcertTicketRespDto;
import id.co.edts.ticketservice.service.ConcertService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ticket")
public class TicketController {
    private ConcertService concertService;

    public TicketController(ConcertService concertService) {
        this.concertService = concertService;
    }

    @PostMapping("/concerts")
    @ResponseBody
    @Operation(
            summary = "Get concert list",
            description = "Retrieve a list of concerts, you can search by concert name, concert date, venue name, or concert location."
    )
    PageableResponseDto<List<ConcertRespDto>> list(@RequestBody CriteriaField param, HttpServletRequest request){
        return concertService.list(param, request);
    }

    @PostMapping("/concerts/{concert_id}/tickets")
    @ResponseBody
    @Operation(
            summary = "Get concert ticket",
            description = "Retrieve concerts ticket information."
    )
    ResponseDto<ConcertTicketRespDto> getConcertTicket(@PathVariable("concert_id") Long concertId, HttpServletRequest request){
        return concertService.getConcertTicket(concertId, request);
    }

    @PostMapping("/concerts/booking/tickets")
    @ResponseBody
    @Operation(
            summary = "Booking concert ticket",
            description = "Booking concert ticket customer."
    )
    ResponseDto<ConcertTicketRespDto> bookingConcertTicket(@RequestBody ConcertBookingTicketReqDto param, HttpServletRequest request){
        return concertService.bookingConcertTicket(param, request);
    }
}
