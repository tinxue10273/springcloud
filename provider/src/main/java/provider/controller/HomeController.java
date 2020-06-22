package provider.controller;

import provider.request.PageRequest;
import provider.response.BaseResponse;
import provider.service.DiscussPostService;
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
public class HomeController{

    @Autowired
    private DiscussPostService discussPostService;


    @RequestMapping(path = "/index", method = RequestMethod.GET)
    public BaseResponse getIndexPage(@Valid @RequestBody PageRequest request,
                                     HttpServletRequest servletRequest) {
        return discussPostService.getIndexPage(request);
    }
}
