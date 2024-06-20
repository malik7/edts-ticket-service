package id.co.edts.ticketservice.controller;

import id.co.edts.apicore.dto.PageableResponseDto;
import id.co.edts.apicore.dto.ResponseDto;
import id.co.edts.apicore.query.dto.CriteriaField;
import id.co.edts.ticketservice.dto.ConcertRespDto;
import id.co.edts.ticketservice.dto.UserTicketReqDto;
import id.co.edts.ticketservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/ticket")
    @ResponseBody
    @Operation(
            summary = "Get user ticket order",
            description = "Retrieve user ticket concerts information."
    )
    ResponseDto getUserTicket(UserTicketReqDto param, HttpServletRequest request){
        return userService.getUserTicket(param, request);
    }
}
