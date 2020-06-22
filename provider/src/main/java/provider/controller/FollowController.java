package provider.controller;

import provider.request.FollowRequest;
import provider.response.BaseResponse;
import provider.service.FollowService;
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
public class FollowController{
    @Autowired
    private FollowService followService;

    @RequestMapping(path = "/follow", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse follow(@Valid @RequestBody FollowRequest request,
                               HttpServletRequest servletRequest) {
        return followService.follow(request);
    }

    @RequestMapping(path = "/unfollow", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse unfollow(@Valid @RequestBody FollowRequest request,
                           HttpServletRequest servletRequest) {
        return followService.unfollow(request);
    }

    @RequestMapping(path = "/followees", method = RequestMethod.POST)
    public BaseResponse getFollowees(@Valid @RequestBody FollowRequest request,
                                     HttpServletRequest servletRequest) {
       return followService.findFollowees(request);
    }

    @RequestMapping(path = "/followers", method = RequestMethod.POST)
    public BaseResponse getFollowers(@Valid @RequestBody FollowRequest request,
                                     HttpServletRequest servletRequest) {
       return followService.findFollowers(request);
    }

    @RequestMapping(path = "/find/followee", method = RequestMethod.POST)
    public BaseResponse findFolloweeCount(@Valid @RequestBody FollowRequest request,
                                     HttpServletRequest servletRequest) {
        return followService.findFolloweeCount(request);
    }

    @RequestMapping(path = "/find/follower", method = RequestMethod.POST)
    public BaseResponse findFollowerCount(@Valid @RequestBody FollowRequest request,
                                     HttpServletRequest servletRequest) {
        return followService.findFollowerCount(request);
    }

    @RequestMapping(path = "/hasFolowed", method = RequestMethod.POST)
    public BaseResponse hasFollowed(@Valid @RequestBody FollowRequest request,
                                     HttpServletRequest servletRequest) {
        return followService.hasFollowed(request);
    }




}
