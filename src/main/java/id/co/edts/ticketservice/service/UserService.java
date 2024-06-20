package id.co.edts.ticketservice.service;

import id.co.edts.apicore.dto.ResponseDto;
import id.co.edts.ticketservice.dto.UserTicketReqDto;
import id.co.edts.ticketservice.dto.UserTicketRespDto;
import jakarta.servlet.http.HttpServletRequest;

public interface UserService {
    ResponseDto<UserTicketRespDto> getUserTicket(UserTicketReqDto param, HttpServletRequest request);
}
