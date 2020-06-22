package provider.controller;

import provider.request.AdminRequest;
import provider.request.BaseRequest;
import provider.request.DiscussRequest;
import provider.response.BaseResponse;
import provider.service.DiscussPostService;
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
@RequestMapping("/discuss")
public class DiscussPostController{
    @Autowired
    private DiscussPostService discussPostService;

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse add(@Valid @RequestBody DiscussRequest request,
                            HttpServletRequest servletRequest) {

        return discussPostService.add(request);
    }

    @RequestMapping(path = "/get", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse get(@Valid @RequestBody BaseRequest request,
                            HttpServletRequest servletRequest) {
       return discussPostService.get(request);
    }

    @RequestMapping(path = "/top", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse setTop(@Valid @RequestBody AdminRequest request,
                         HttpServletRequest servletRequest) {
       return discussPostService.top(request);

    }

    @RequestMapping(path = "/wonderful", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse setWonderful(@Valid @RequestBody BaseRequest request,
                               HttpServletRequest servletRequest) {
        return discussPostService.wonderful(request);
    }

    @RequestMapping(path = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse setDelete(@Valid @RequestBody BaseRequest request,
                                  HttpServletRequest servletRequest) {
        return discussPostService.delete(request);
    }

}
