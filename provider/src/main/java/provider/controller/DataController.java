package provider.controller;

import provider.request.DataRequest;
import provider.response.BaseResponse;
import provider.service.DataService;
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
@RequestMapping(path = "/data")
@Controller
public class DataController {
    @Autowired
    private DataService dataService;

    // 统计网站UV
    @RequestMapping(path = "/uv", method = RequestMethod.POST)
    public BaseResponse getUV(@Valid @RequestBody DataRequest request,
                              HttpServletRequest servletRequest) {
        return dataService.getUV(request);
    }

    // 统计活跃用户
    @RequestMapping(path = "/dau", method = RequestMethod.POST)
    public BaseResponse getDAU(@Valid @RequestBody DataRequest request,
                               HttpServletRequest servletRequest) {
        return dataService.getDAU(request);
    }
}
