package consumer.controller;

import consumer.request.AdminRequest;
import consumer.request.BaseRequest;
import consumer.request.DiscussRequest;
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
@RequestMapping("/discuss")
public class DiscussPostController{
    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse addDiscussPost(@Valid @RequestBody DiscussRequest request,
                                 HttpServletRequest servletRequest) {
        request.setTicket(servletRequest.getHeader("ticket"));
        return this.restTemplate.postForObject("http://localhost:8080/cycle/provider/discuss/add", request, BaseResponse.class);
    }

    @RequestMapping(path = "/get", method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse getDiscussPost(@PathVariable("discussPostId") Long discussPostId,
                                       HttpServletRequest servletRequest) {
        BaseRequest request = new BaseRequest();
        request.setId(discussPostId);
        request.setTicket(servletRequest.getHeader("ticket"));
        return this.restTemplate.postForObject("http://localhost:8080/cycle/provider/discuss/get", request, BaseResponse.class);
    }

    // 置顶
    @RequestMapping(path = "/top", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse setTop(@Valid @RequestBody AdminRequest request,
                         HttpServletRequest servletRequest) {
        request.setTicket(servletRequest.getHeader("ticket"));
        return this.restTemplate.postForObject("http://localhost:8080/cycle/provider/discuss/top", request, BaseResponse.class);
    }

    // 加精
    @RequestMapping(path = "/wonderful", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse setWonderful(@Valid @RequestBody AdminRequest request,
                               HttpServletRequest servletRequest) {
        request.setTicket(servletRequest.getHeader("ticket"));
        return this.restTemplate.postForObject("http://localhost:8080/cycle/provider/discuss/wonderful", request, BaseResponse.class);
    }

    // 删除
    @RequestMapping(path = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse setDelete(@Valid @RequestBody AdminRequest request,
                                  HttpServletRequest servletRequest) {
        request.setTicket(servletRequest.getHeader("ticket"));
        return this.restTemplate.postForObject("http://localhost:8080/cycle/provider/discuss/delete", request, BaseResponse.class);
    }

}
