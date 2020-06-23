package provider.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.util.HtmlUtils;
import provider.common.CycleErrorCode;
import provider.common.HostHolder;
import provider.common.SensitiveFilter;
import provider.convertor.DiscussPostConvertor;
import provider.domain.UserDO;
import provider.dto.EventDTO;
import provider.kafka.EventProducer;
import provider.repository.impl.DiscussPostDORepositoryImpl;
import provider.repository.impl.UserDORepositoryImpl;
import provider.request.BaseRequest;
import provider.request.DiscussRequest;
import provider.request.PageRequest;
import provider.response.BaseResponse;
import provider.vo.DiscussPostVO;
import provider.vo.UserVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static provider.common.CycleConstant.ENTITY_TYPE_POST;
import static provider.common.CycleConstant.TOPIC_PUBLISH;

@Slf4j
@Service
public class DiscussPostService {

    @Autowired
    private DiscussPostDORepositoryImpl discussPostDORepository;

    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private EventProducer eventProducer;

    @Autowired
    private UserDORepositoryImpl userDORepository;

    @Autowired
    private LikeService likeService;

    public List<DiscussPostVO> list(int userId, int offset, int limit, int orderMode) {
        return DiscussPostConvertor.convertVOs(discussPostDORepository.list(userId, offset, limit, orderMode));
    }

    public int countByUser(int userId) {
        return discussPostDORepository.countByUser(userId);
    }

    public BaseResponse add(DiscussRequest request) {
        return add(request.getDiscussPostVO());
    }

    private BaseResponse add(DiscussPostVO post) {
        if (post == null) {
            throw new IllegalArgumentException("参数不能为空!");
        }
        UserVO user = hostHolder.getUser();
        if (user == null) {
            return CycleErrorCode.NO_LOGIN.getResponse();
        }
        post.setTitle(HtmlUtils.htmlEscape(post.getTitle()));
        post.setContent(HtmlUtils.htmlEscape(post.getContent()));
        post.setTitle(sensitiveFilter.filter(post.getTitle()));
        post.setContent(sensitiveFilter.filter(post.getContent()));
        if (discussPostDORepository.insert(DiscussPostConvertor.convertDO(post))) {
            return BaseResponse.builder().success(true).build();
        }
        EventDTO event = EventDTO.builder()
                .topic(TOPIC_PUBLISH)
                .userId(user.getId())
                .entityType(ENTITY_TYPE_POST)
                .entityId(post.getId())
                .build();
        eventProducer.fireEvent(event);
        return CycleErrorCode.INSERT_ERROR.getResponse();
    }


    public BaseResponse get(BaseRequest request) {
        return BaseResponse.builder().success(true).result(get(request.getId())).build();
    }

    public DiscussPostVO get(int id) {
        return DiscussPostConvertor.convertVO(discussPostDORepository.get(id));
    }

    public int updateCommentCount(int id, int commentCount) {
        return discussPostDORepository.updateCommentCount(id, commentCount);
    }

    public int update(DiscussPostVO post) {
        return discussPostDORepository.update(post);

    }

    public BaseResponse top(BaseRequest request) {
        discussPostDORepository.update(DiscussPostVO.builder().id(request.getId()).status(1).build());

        EventDTO event = EventDTO.builder().topic(TOPIC_PUBLISH).userId(hostHolder.getUser().getId())
                .entityType(ENTITY_TYPE_POST).entityId(request.getId()).build();

        eventProducer.fireEvent(event);

        return BaseResponse.builder().success(true).build();
    }

    public BaseResponse wonderful(BaseRequest request) {
        discussPostDORepository.update(DiscussPostVO.builder().id(request.getId()).status(1).build());

        EventDTO event = EventDTO.builder().topic(TOPIC_PUBLISH).userId(hostHolder.getUser().getId())
                .entityType(ENTITY_TYPE_POST).entityId(request.getId()).build();
        eventProducer.fireEvent(event);

        return BaseResponse.builder().success(true).build();

    }

    public BaseResponse delete(BaseRequest request) {

        discussPostDORepository.update(DiscussPostVO.builder().id(request.getId()).status(1).build());

        EventDTO event = EventDTO.builder().topic(TOPIC_PUBLISH).userId(hostHolder.getUser().getId())
                .entityType(ENTITY_TYPE_POST).entityId(request.getId()).build();
        eventProducer.fireEvent(event);

        return BaseResponse.builder().success(true).build();
    }

    public BaseResponse getIndexPage(PageRequest request) {
        Integer limit = request.getPageSize();
        Integer offset = (request.getCurrent() - 1) * request.getPageSize();
        List<DiscussPostVO> posts = list(0, offset, limit, 0);
        List<Map<String, Object>> discussPosts = new ArrayList<>();
        if (!ObjectUtils.isEmpty(posts)) {
            for (DiscussPostVO post : posts) {
                Map<String, Object> map = new HashMap<>();
                map.put("post", post);
                UserDO user = userDORepository.getById(post.getUserId());
                map.put("user", user);

                long likeCount = likeService.count(ENTITY_TYPE_POST, post.getId());
                map.put("likeCount", likeCount);
                discussPosts.add(map);
            }
        }
        return null;
    }
}
