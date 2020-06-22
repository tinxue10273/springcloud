package consumer.controller;

import consumer.request.DataRequest;
import consumer.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @authorgouhuo on 2020/04/27.
 */
@RequestMapping(path = "/data")
@Controller
public class DataController {
    @Autowired
    private RestTemplate restTemplate;

    // 统计网站UV
    @RequestMapping(path = "/uv", method = RequestMethod.POST)
    public BaseResponse getUV(@Valid @RequestBody DataRequest request,
                        HttpServletRequest servletRequest) {
        request.setTicket(servletRequest.getHeader("ticket"));
        return this.restTemplate.postForObject("http://localhost:8080/cycle/provider/data/uv", request, BaseResponse.class);
    }

    // 统计活跃用户
    @RequestMapping(path = "/dau", method = RequestMethod.POST)
    public BaseResponse getDAU(@Valid @RequestBody DataRequest request,
                               HttpServletRequest servletRequest) {
        request.setTicket(servletRequest.getHeader("ticket"));
        return this.restTemplate.postForObject("http://localhost:8080/cycle/provider/data/dau", request, BaseResponse.class);
    }
}
