package provider.controller;

import provider.request.SearchRequest;
import provider.response.BaseResponse;
import provider.service.ElasticsearchService;
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
public class SearchController {

    @Autowired
    private ElasticsearchService elasticsearchService;
    // search?keyword=xxx
    @RequestMapping(path = "/search", method = RequestMethod.POST)
    public BaseResponse search(@Valid @RequestBody SearchRequest request,
                               HttpServletRequest servletRequest) {
        return elasticsearchService.search(request);
    }

}
