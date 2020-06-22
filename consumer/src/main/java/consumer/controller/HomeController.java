package consumer.controller;

import consumer.request.PageRequest;
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
@Controller
public class HomeController{

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(path = "/index", method = RequestMethod.POST)
    public BaseResponse getIndexPage(@Valid @RequestBody PageRequest request,
                               HttpServletRequest servletRequest) {
        return this.restTemplate.postForObject("http://localhost:8080/cycle/provider/index", request, BaseResponse.class);
    }
}
