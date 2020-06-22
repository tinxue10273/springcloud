package provider.controller;

import provider.request.MessageRequest;
import provider.response.BaseResponse;
import provider.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @authorgouhuo on 2020/04/27.
 */
@RequestMapping(path = "/notice", method = RequestMethod.GET)
@Controller
public class NoticeController {
    @Autowired
    private MessageService messageService;

    @RequestMapping(path = "/get", method = RequestMethod.POST)
    public BaseResponse getNoticeDetail(@Valid @RequestBody MessageRequest request,
                                        HttpServletRequest servletRequest) {
        return messageService.getLatestNotice(request);
    }

    @RequestMapping(path = "/list", method = RequestMethod.POST)
    public BaseResponse getNoticeList(@Valid @RequestBody MessageRequest request,
                                      HttpServletRequest servletRequest) {
        return messageService.listNotice(request);
    }
}
