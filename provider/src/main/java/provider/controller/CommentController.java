package provider.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import provider.request.BaseRequest;
import provider.request.CommentAddRequest;
import provider.response.BaseResponse;
import provider.service.CommentService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @authorgouhuo on 2020/04/27.
 */
@Controller
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public BaseResponse addComment(@Valid @RequestBody CommentAddRequest request,
                                   HttpServletRequest servletRequest) {
        return commentService.add(request);
    }

    // 获取贴子下的所有评论
    @RequestMapping(path = "/list", method = RequestMethod.POST)
    public BaseResponse listComments(@Valid @RequestBody BaseRequest request,
                                     HttpServletRequest servletRequest) {
        return commentService.list(request);
    }


}
