package provider.controller;

import provider.request.LoginRequest;
import provider.request.TicketRequest;
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
 * @author gouhuo on 2020/04/27.
 */
@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse login(@Valid @RequestBody LoginRequest request,
                              HttpServletRequest servletRequest) {
        return userService.login(request);
    }

    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse logout(@Valid @RequestBody TicketRequest request,
                               HttpServletRequest servletRequest) {
        return userService.logout(request);
    }

}
