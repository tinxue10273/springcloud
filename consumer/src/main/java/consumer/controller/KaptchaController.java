package consumer.controller;

import consumer.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;

/**
 * @authorgouhuo on 2020/04/27.
 */
@Controller
public class KaptchaController {
    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(path = "/kaptcha", method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse getKaptcha() {
        return this.restTemplate.getForObject("http://localhost:8080/cycle/provider/kaptcha", BaseResponse.class);
    }

}
