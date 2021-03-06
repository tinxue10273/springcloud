package provider.controller;

import provider.request.CommentAddRequest;
import provider.response.BaseResponse;
import provider.service.CommentService;
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
}
