package consumer.controller;

import consumer.request.BaseRequest;
import consumer.request.MessageRequest;
import consumer.request.PageRequest;
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
@RequestMapping(path = "/letter", method = RequestMethod.GET)
@Controller
public class MessageController {

    @Autowired
    private RestTemplate restTemplate;


    @RequestMapping(path = "/add", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse add(@Valid @RequestBody MessageRequest request,
                            HttpServletRequest servletRequest) {
        request.setTicket(servletRequest.getHeader("ticket"));
        return this.restTemplate.postForObject("http://localhost:8080/cycle/provider/letter/add", request, BaseResponse.class);
    }


    // 私信列表
    @RequestMapping(path = "/list", method = RequestMethod.GET)
    public BaseResponse list(@Valid @RequestBody PageRequest request,
                             HttpServletRequest servletRequest) {
        request.setTicket(servletRequest.getHeader("ticket"));
        return this.restTemplate.postForObject("http://localhost:8080/cycle/provider/letter/list", request, BaseResponse.class);
    }

    @RequestMapping(path = "/get/{conversationId}", method = RequestMethod.GET)
    public BaseResponse get(@PathVariable("conversationId") String conversationId,
                            HttpServletRequest servletRequest) {
        BaseRequest request = new BaseRequest();
        request.setOther(conversationId);
        request.setTicket(servletRequest.getHeader("ticket"));
        return this.restTemplate.postForObject("http://localhost:8080/cycle/provider/letter/get", request, BaseResponse.class);
    }
}
