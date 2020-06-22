package consumer.controller;

import consumer.request.CommentAddRequest;
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
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public BaseResponse addComment(@Valid @RequestBody CommentAddRequest request,
                                   HttpServletRequest servletRequest) {
        request.setTicket(servletRequest.getHeader("ticket"));
        return this.restTemplate.postForObject("http://localhost:8080/cycle/provider/comment/add", request, BaseResponse.class);
    }
}
