package provider.controller;

import provider.request.UserRequest;
import provider.response.BaseResponse;
import provider.service.UserService;
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
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    // 更新头像路径
    @RequestMapping(path = "/url", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse updateHeaderUrl(@Valid @RequestBody UserRequest request,
                                  HttpServletRequest servletRequest) {
        return userService.setUrl(request);
    }


    // 个人主页
    @RequestMapping(path = "/profile", method = RequestMethod.POST)
    public BaseResponse getProfilePage(@Valid @RequestBody UserRequest request,
                                       HttpServletRequest servletRequest) {
       return userService.getProfilePage(request);
    }

}
