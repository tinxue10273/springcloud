package provider.service;

import org.springframework.data.redis.core.RedisTemplate;
import provider.common.HostHolder;
import provider.common.RedisKeyUtil;
import provider.common.SensitiveFilter;
import provider.convertor.CommentConvertor;
import provider.domain.CommentDO;
import provider.domain.DiscussPostDO;
import provider.dto.EventDTO;
import provider.kafka.EventProducer;
import provider.repository.impl.CommonDORepositoryImpl;
import provider.repository.impl.DiscussPostDORepositoryImpl;
import provider.request.CommentAddRequest;
import provider.response.BaseResponse;
import provider.vo.CommentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static provider.common.CycleConstant.ENTITY_TYPE_COMMENT;
import static provider.common.CycleConstant.ENTITY_TYPE_POST;
import static provider.common.CycleConstant.TOPIC_COMMENT;
import static provider.common.CycleConstant.TOPIC_PUBLISH;

@Service
public class CommentService {

    @Autowired
    private CommonDORepositoryImpl commonDORepository;

    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private EventProducer eventProducer;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private DiscussPostDORepositoryImpl discussPostDORepository;

    public List<CommentDO> list(int entityType, int entityId, int offset, int limit) {
        return commonDORepository.list(entityType, entityId, offset, limit);
    }

    public int count(int entityType, int entityId) {
        return commonDORepository.count(entityType, entityId);
    }


    public CommentVO get(int id) {
        return CommentConvertor.convertorVO(commonDORepository.get(id));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public BaseResponse add(CommentAddRequest request) {
        CommentVO comment = request.getComment();
        Integer discussPostId = request.getDiscussId();
        if (comment == null) {
            throw new IllegalArgumentException("参数不能为空!");
        }

        comment.setContent(HtmlUtils.htmlEscape(comment.getContent()));
        comment.setContent(sensitiveFilter.filter(comment.getContent()));
        int rows = commonDORepository.insert(CommentConvertor.convertorDO(comment));

        if (comment.getEntityType() == ENTITY_TYPE_POST) {
            int count = commonDORepository.count(comment.getEntityType(), comment.getEntityId());
            discussPostDORepository.updateCommentCount(comment.getEntityId(), count);
        }

        EventDTO event;
        event = new EventDTO().builder()
                .topic(TOPIC_COMMENT)
                .userId(hostHolder.getUser().getId())
                .entityType(comment.getEntityType())
                .entityId(comment.getEntityId())
                .build();
        Map<String, Object> data = new HashMap<>();
        data.put("postId", discussPostId);
        event.setData(data);
        if (comment.getEntityType() == ENTITY_TYPE_POST) {
            DiscussPostDO target = discussPostDORepository.get(comment.getEntityId());
            event.setEntityUserId(Integer.valueOf(target.getUserId()));
        } else if (comment.getEntityType() == ENTITY_TYPE_COMMENT) {
            CommentDO target = commonDORepository.get(comment.getEntityId());
            event.setEntityUserId(target.getUserId());
        }
        eventProducer.fireEvent(event);

        if (comment.getEntityType() == ENTITY_TYPE_POST) {
            event = new EventDTO().builder()
                    .topic(TOPIC_PUBLISH)
                    .userId(comment.getUserId())
                    .entityType(ENTITY_TYPE_POST)
                    .entityId(discussPostId)
                    .build();
            eventProducer.fireEvent(event);
            String redisKey = RedisKeyUtil.getPostScoreKey();
            redisTemplate.opsForSet().add(redisKey, discussPostId);
        }
        return BaseResponse.builder().success(true).result(rows).build();
    }
}
