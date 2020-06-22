package provider.service;

import provider.common.HostHolder;
import provider.common.RedisKeyUtil;
import provider.domain.UserDO;
import provider.dto.EventDTO;
import provider.kafka.EventProducer;
import provider.repository.impl.UserDORepositoryImpl;
import provider.request.FollowRequest;
import provider.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;
import provider.vo.UserVO;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static provider.common.CycleConstant.ENTITY_TYPE_USER;
import static provider.common.CycleConstant.TOPIC_FOLLOW;

@Service
public class FollowService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserDORepositoryImpl userDORepository;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private EventProducer eventProducer;

    public BaseResponse follow(FollowRequest request) {
        UserVO user = hostHolder.getUser();
        follow(user.getId(), request.getEntityType(), request.getEntityId());
        return BaseResponse.builder().success(true).build();
    }

    private void follow(int userId, int entityType, int entityId) {
        redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                String followeeKey = RedisKeyUtil.getFolloweeKey(userId, entityType);
                String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);

                operations.multi();

                operations.opsForZSet().add(followeeKey, entityId, System.currentTimeMillis());
                operations.opsForZSet().add(followerKey, userId, System.currentTimeMillis());
                EventDTO event = new EventDTO().builder()
                        .topic(TOPIC_FOLLOW)
                        .userId(hostHolder.getUser().getId())
                        .entityType(entityType)
                        .entityId(entityId)
                        .entityUserId(entityId).build();
                eventProducer.fireEvent(event);
                return operations.exec();
            }
        });
    }

    public BaseResponse unfollow(FollowRequest request) {
        UserVO user = hostHolder.getUser();
        unfollow(user.getId(), request.getEntityType(), request.getEntityId());
        return BaseResponse.builder().success(true).build();
    }

    private void unfollow(int userId, int entityType, int entityId) {
        redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                String followeeKey = RedisKeyUtil.getFolloweeKey(userId, entityType);
                String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);
                operations.multi();
                operations.opsForZSet().remove(followeeKey, entityId);
                operations.opsForZSet().remove(followerKey, userId);

                return operations.exec();
            }
        });
    }

    public BaseResponse findFolloweeCount(FollowRequest request) {
        UserVO user = hostHolder.getUser();
        return BaseResponse.builder().success(true).result(findFolloweeCount(user.getId(), request.getEntityType()))
                .build();
    }

    public long findFolloweeCount(int userId, int entityType) {
        String followeeKey = RedisKeyUtil.getFolloweeKey(userId, entityType);
        return redisTemplate.opsForZSet().zCard(followeeKey);
    }

    public BaseResponse findFollowerCount(FollowRequest request) {
        UserVO user = hostHolder.getUser();
        return BaseResponse.builder().success(true).result(findFollowerCount(user.getId(), request.getEntityType()))
                .build();
    }

    public long findFollowerCount(int entityType, int entityId) {
        String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);
        return redisTemplate.opsForZSet().zCard(followerKey);
    }

    public BaseResponse hasFollowed(FollowRequest request) {
        UserVO user = hostHolder.getUser();
        return BaseResponse.builder().success(true).result(hasFollowed(user.getId(), request.getEntityType(),
                request.getEntityId()))
                .build();
    }

    public boolean hasFollowed(int userId, int entityType, int entityId) {
        String followeeKey = RedisKeyUtil.getFolloweeKey(userId, entityType);
        return redisTemplate.opsForZSet().score(followeeKey, entityId) != null;
    }


    public BaseResponse findFollowees(FollowRequest request) {
        Integer offset = (request.getCurrent() - 1) * request.getPageSize();
        UserVO user = hostHolder.getUser();
        return BaseResponse.builder().success(true).result(findFollowees(user.getId(), offset,
                request.getPageSize())).build();
    }

    private List<Map<String, Object>> findFollowees(int userId, int offset, int limit) {
        String followeeKey = RedisKeyUtil.getFolloweeKey(userId, ENTITY_TYPE_USER);
        Set<Integer> targetIds = redisTemplate.opsForZSet().reverseRange(followeeKey, offset, offset + limit - 1);

        if (targetIds == null) {
            return null;
        }

        List<Map<String, Object>> list = new ArrayList<>();
        for (Integer targetId : targetIds) {
            Map<String, Object> map = new HashMap<>();
            UserDO user = userDORepository.get(targetId);
            map.put("user", user);
            Double score = redisTemplate.opsForZSet().score(followeeKey, targetId);
            map.put("followTime", new Date(score.longValue()));
            list.add(map);
        }

        return list;
    }

    public BaseResponse findFollowers(FollowRequest request) {
        Integer offset = (request.getCurrent() - 1) * request.getPageSize();
        UserVO user = hostHolder.getUser();
        return BaseResponse.builder().success(true).result(findFollowers(user.getId(), offset,
                request.getPageSize())).build();
    }

    private List<Map<String, Object>> findFollowers(int userId, int offset, int limit) {
        String followerKey = RedisKeyUtil.getFollowerKey(ENTITY_TYPE_USER, userId);
        Set<Integer> targetIds = redisTemplate.opsForZSet().reverseRange(followerKey, offset, offset + limit - 1);

        if (targetIds == null) {
            return null;
        }

        List<Map<String, Object>> list = new ArrayList<>();
        for (Integer targetId : targetIds) {
            Map<String, Object> map = new HashMap<>();
            UserDO user = userDORepository.get(targetId);
            map.put("user", user);
            Double score = redisTemplate.opsForZSet().score(followerKey, targetId);
            map.put("followTime", new Date(score.longValue()));
            list.add(map);
        }

        return list;
    }

}
