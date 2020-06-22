package consumer.controller;

import consumer.request.LoginRequest;
import consumer.request.TicketRequest;
import consumer.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;

/**
 * @author gouhuo on 2020/04/27.
 */
@Controller
public class LoginController {

    @Autowired
    private RestTemplate restTemplate;


    // http://localhost:8080/community/activation/101/code

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse login(@Valid @RequestBody LoginRequest request,
                              HttpServletRequest servletRequest) {
        request.setTicket(servletRequest.getHeader("ticket"));
        return this.restTemplate.postForObject("http://localhost:8080/cycle/provider/login", request, BaseResponse.class);
    }

    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse logout(@CookieValue("ticket") String ticket,
                               HttpServletRequest servletRequest) {
        TicketRequest request= TicketRequest.builder().ticket(ticket).build();
        request.setTicket(servletRequest.getHeader("ticket"));
        return this.restTemplate.postForObject("http://localhost:8080/cycle/provider/logout", request, BaseResponse.class);
    }

}
