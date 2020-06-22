package consumer.controller;

import consumer.request.ActivationRequest;
import consumer.request.RegisterRequest;
import consumer.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @authorgouhuo on 2020/04/27.
 */
@Controller
public class RegisterController {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(path = "/activation/{userId}/{code}", method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse activation(@PathVariable("userId") long userId, @PathVariable("code") String code,
                                   HttpServletRequest servletRequest) {
        ActivationRequest request = ActivationRequest.builder().userId(userId).code(code).build();
        request.setTicket(servletRequest.getHeader("ticket"));
        return this.restTemplate.postForObject("http://localhost:8080/cycle/provider/activation", request,BaseResponse.class);
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse register(@Valid @RequestBody RegisterRequest request,
                                 HttpServletRequest servletRequest) {
        request.setTicket(servletRequest.getHeader("ticket"));
        return this.restTemplate.postForObject("http://localhost:8080/provider/demo", request,BaseResponse.class);
    }

}
