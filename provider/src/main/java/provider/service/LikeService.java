package provider.service;

import provider.common.HostHolder;
import provider.common.RedisKeyUtil;
import provider.dto.EventDTO;
import provider.kafka.EventProducer;
import provider.request.LikeRequest;
import provider.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;
import provider.vo.UserVO;

import java.util.HashMap;
import java.util.Map;

import static provider.common.CycleConstant.TOPIC_LIKE;

@Service
public class LikeService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private EventProducer eventProducer;

    public BaseResponse count(LikeRequest request) {
        return BaseResponse.builder().success(true).result(count(request.getEntityType(), request.getEntityId()))
                .build();
    }

    public BaseResponse like(LikeRequest request){
        UserVO user = hostHolder.getUser();
        Integer entityType = Integer.valueOf(request.getEntityType());
        Integer entityId = request.getEntityId();
        Integer entityUserId = request.getEntityUserId();
        Integer postId = request.getPostId();
        like(user.getId(), entityType, entityId, entityUserId);
        Integer likeStatus = getStatus(user.getId(), entityType, entityId);
        if(likeStatus.equals(1)){
            EventDTO event = new EventDTO().builder()
                    .topic(TOPIC_LIKE)
                    .userId(user.getId())
                    .entityType(entityType)
                    .entityId(entityId)
                    .entityUserId(entityUserId).build();
            Map<String, Object> data = new HashMap<>();
            data.put("postId", postId);
            event.setData(data);
            eventProducer.fireEvent(event);
        }
        return BaseResponse.builder().success(true).build();
    }

    private void like(int userId, int entityType, int entityId, int entityUserId) {
        redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType, entityId);
                String userLikeKey = RedisKeyUtil.getUserLikeKey(entityUserId);

                boolean isMember = operations.opsForSet().isMember(entityLikeKey, userId);

                operations.multi();

                if (isMember) {
                    operations.opsForSet().remove(entityLikeKey, userId);
                    operations.opsForValue().decrement(userLikeKey);
                } else {
                    operations.opsForSet().add(entityLikeKey, userId);
                    operations.opsForValue().increment(userLikeKey);
                }

                return operations.exec();
            }
        });
    }

    public long count(int entityType, int entityId) {
        String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType, entityId);
        return redisTemplate.opsForSet().size(entityLikeKey);
    }

    public int count(int userId) {
        String userLikeKey = RedisKeyUtil.getUserLikeKey(userId);
        Integer count = (Integer) redisTemplate.opsForValue().get(userLikeKey);
        return count == null ? 0 : count.intValue();
    }

    public int getStatus(int userId, int entityType, int entityId) {
        String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType, entityId);
        return redisTemplate.opsForSet().isMember(entityLikeKey, userId) ? 1 : 0;
    }
}
