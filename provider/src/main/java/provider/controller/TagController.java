package provider.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import provider.request.SearchRequest;
import provider.request.SetTagRequest;
import provider.response.BaseResponse;
import provider.service.TagService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @authorgouhuo on 2020/06/23.
 */
@Controller
public class TagController {

    @Autowired
    private TagService tagService;

    @RequestMapping(path = "/tag/list", method = RequestMethod.GET)
    public BaseResponse listTag() {
        return tagService.listTag();
    }

    @RequestMapping(path = "/tag/set", method = RequestMethod.POST)
    public BaseResponse setTag(@Valid @RequestBody SetTagRequest request,
                               HttpServletRequest servletRequest) {
        return tagService.setTag(request);
    }


}
