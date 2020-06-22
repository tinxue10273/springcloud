package provider.controller;

import provider.request.MessageRequest;
import provider.response.BaseResponse;
import provider.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @authorgouhuo on 2020/04/27.
 */
@RequestMapping(path = "/letter", method = RequestMethod.GET)
@Controller
public class MessageController {

    @Autowired
    private MessageService messageService;


    @RequestMapping(path = "/add", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse add(@Valid @RequestBody MessageRequest request,
                            HttpServletRequest servletRequest) {
        return messageService.add(request);
    }

    @RequestMapping(path = "/list", method = RequestMethod.POST)
    public BaseResponse list(@Valid @RequestBody MessageRequest request,
                             HttpServletRequest servletRequest) {
        return messageService.listLetters(request);
    }

    @RequestMapping(path = "/get", method = RequestMethod.POST)
    public BaseResponse get(@Valid @RequestBody MessageRequest request,
                            HttpServletRequest servletRequest) {
       return messageService.listConversations(request);
    }
}
