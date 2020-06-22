package provider.controller;

import provider.response.BaseResponse;
import provider.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * @authorgouhuo on 2020/04/27.
 */
@Controller
public class KaptchaController {
    @Autowired
    private UserService userService;

    @RequestMapping(path = "/kaptcha", method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse getKaptcha(HttpServletResponse response/*, HttpSession session*/) {
        return userService.getKaptcha();
    }

}
