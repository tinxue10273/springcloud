package provider.controller;

import provider.request.ActivationRequest;
import provider.request.RegisterRequest;
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
public class RegisterController {

    @Autowired
    private UserService userService;

    @RequestMapping(path = "/activation", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse activation(@Valid @RequestBody ActivationRequest request,
                                   HttpServletRequest servletRequest) {
       return userService.activation(request);
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse register(@Valid @RequestBody RegisterRequest request,
                                 HttpServletRequest servletRequest) {
       return userService.register(request);
    }

}
