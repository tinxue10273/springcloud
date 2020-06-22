package consumer.controller;

import consumer.request.BaseRequest;
import consumer.request.FollowRequest;
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
public class FollowController{
    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(path = "/follow", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse follow(@Valid @RequestBody FollowRequest request,
                               HttpServletRequest servletRequest) {
        request.setTicket(servletRequest.getHeader("ticket"));
        return this.restTemplate.postForObject("http://localhost:8080/cycle/provider/follow", request, BaseResponse.class);
    }

    @RequestMapping(path = "/unfollow", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse unfollow(@Valid @RequestBody FollowRequest request,
                           HttpServletRequest servletRequest) {
        request.setTicket(servletRequest.getHeader("ticket"));
        return this.restTemplate.postForObject("http://localhost:8080/cycle/provider/unfollow", request, BaseResponse.class);
    }

    @RequestMapping(path = "/followees/{userId}", method = RequestMethod.GET)
    public BaseResponse getFollowees(@PathVariable("userId") Long userId ,
                                     HttpServletRequest servletRequest) {
        BaseRequest request = new BaseRequest();
        request.setId(userId);
        request.setTicket(servletRequest.getHeader("ticket"));
        return this.restTemplate.postForObject("http://localhost:8080/cycle/provider/followees", request, BaseResponse.class);
    }

    @RequestMapping(path = "/followers/{userId}", method = RequestMethod.GET)
    public BaseResponse getFollowers(@PathVariable("userId") Long userId,
                                     HttpServletRequest servletRequest) {
        BaseRequest request = new BaseRequest();
        request.setId(userId);
        request.setTicket(servletRequest.getHeader("ticket"));
        return this.restTemplate.postForObject("http://localhost:8080/cycle/provider/followers", request, BaseResponse.class);
    }
}
