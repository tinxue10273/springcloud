package consumer.controller;

import consumer.request.BaseRequest;
import consumer.request.UserRequest;
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
@RequestMapping("/user")
public class UserController {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(path = "/setting", method = RequestMethod.GET)
    public BaseResponse getSettingPage(@Valid @RequestBody UserRequest request,
                                 HttpServletRequest servletRequest) {
        request.setTicket(servletRequest.getHeader("ticket"));
        return this.restTemplate.postForObject("http://localhost:8080/cycle/provider/user/setting", request,BaseResponse.class);
    }

    @RequestMapping(path = "/url", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse updateHeaderUrl(@Valid @RequestBody UserRequest request,
                                  HttpServletRequest servletRequest) {
        request.setTicket(servletRequest.getHeader("ticket"));
        return this.restTemplate.postForObject("http://localhost:8080/cycle/provider/user/url", request, BaseResponse.class);
    }

    @RequestMapping(path = "/profile/{userId}", method = RequestMethod.GET)
    public BaseResponse getProfilePage(@PathVariable("userId") Long userId,
                                       HttpServletRequest servletRequest) {
        BaseRequest request = new BaseRequest();
        request.setId(userId);
        request.setTicket(servletRequest.getHeader("ticket"));
        return this.restTemplate.postForObject("http://localhost:8080/cycle/provider/user/profile", request, BaseResponse.class);
    }

}
