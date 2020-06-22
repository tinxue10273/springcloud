package provider.controller;

import provider.request.LikeRequest;
import provider.response.BaseResponse;
import provider.service.LikeService;
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
@Controller
public class LikeController {

    @Autowired
    private LikeService likeService;

    @RequestMapping(path = "/like", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse like(@Valid @RequestBody LikeRequest request,
                             HttpServletRequest servletRequest) {
        return likeService.like(request);
    }

    @RequestMapping(path = "/like/count", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse count(@Valid @RequestBody LikeRequest request,
                             HttpServletRequest servletRequest) {
        return likeService.count(request);
    }


}
