package id.co.edts.ticketservice.service;

import id.co.edts.apicore.dto.PageableResponseDto;
import id.co.edts.apicore.dto.ResponseDto;
import id.co.edts.apicore.query.dto.CriteriaField;
import id.co.edts.ticketservice.dto.ConcertBookingTicketReqDto;
import id.co.edts.ticketservice.dto.ConcertRespDto;
import id.co.edts.ticketservice.dto.ConcertTicketRespDto;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface ConcertService {
    PageableResponseDto<List<ConcertRespDto>> list(CriteriaField param, HttpServletRequest request);
    ResponseDto<ConcertTicketRespDto> getConcertTicket(Long concertId, HttpServletRequest request);
    ResponseDto bookingConcertTicket(ConcertBookingTicketReqDto param, HttpServletRequest request);
}
