package consumer.controller;

import consumer.request.BaseRequest;
import consumer.request.PageRequest;
import consumer.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

/**
 * @authorgouhuo on 2020/04/27.
 */
@RequestMapping(path = "/notice", method = RequestMethod.GET)
@Controller
public class NoticeController {
    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(path = "/get/{topic}", method = RequestMethod.GET)
    public BaseResponse getNoticeDetail(@PathVariable("topic") String topic,
                                        HttpServletRequest servletRequest) {
        BaseRequest request = new BaseRequest();
        request.setOther(topic);
        request.setTicket(servletRequest.getHeader("ticket"));
        return this.restTemplate.postForObject("http://localhost:8080/cycle/provider/notice/get", request, BaseResponse.class);
    }

    @RequestMapping(path = "/list", method = RequestMethod.GET)
    public BaseResponse getNoticeList(PageRequest request,
                                      HttpServletRequest servletRequest) {
        request.setTicket(servletRequest.getHeader("ticket"));
        return this.restTemplate.postForObject("http://localhost:8080/cycle/provider/notice/list", request, BaseResponse.class);
        }
}
