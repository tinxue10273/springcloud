package provider.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import provider.response.BaseResponse;
import provider.service.TagService;

/**
 * @authorgouhuo on 2020/06/23.
 */
@Controller
public class TagController {

    @Autowired
    private TagService tagService;

    @RequestMapping(path = "tag/list", method = RequestMethod.GET)
    public BaseResponse getIndexPage() {
        return tagService.listTag();
    }
}
