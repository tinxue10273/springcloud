package provider.service;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import provider.common.CycleErrorCode;
import provider.common.JsonTool;
import provider.request.SetTagRequest;
import provider.response.BaseResponse;

import java.util.HashSet;
import java.util.Set;

/**
 * @authorgouhuo on 2020/06/23.
 */
@Service
public class TagService {

    @Autowired
    private RedisTemplate redisTemplate;

    private static final String INTEGER_TAG = "integer:tag";

    private static final String POST_TAG = "post:tag";

    public void setTag(Set<String> tags){
        if(ObjectUtils.isEmpty(tags)){
            return;
        }
        String tagStr = JsonTool.toJson(tags);
        redisTemplate.opsForValue().set(INTEGER_TAG, tagStr);
    }

    public BaseResponse listTag(){
        Set<String> tags = getTags();
        return BaseResponse.builder().success(true).result(tags).build();
    }

    public Set<String> getTags(){
        String tagStr = (String) redisTemplate.opsForValue().get(INTEGER_TAG);
        return JsonTool.fromJson(tagStr, new TypeReference<Set<String>>() {
        });
    }

    public void setDiscussPostTags(int discussPostId, Set<String> tags){
        if(ObjectUtils.isEmpty(tags) || ObjectUtils.isEmpty(discussPostId)){
            return;
        }
        for(String tag : tags){
            String  postIdsStr = (String) redisTemplate.opsForHash().get(POST_TAG, tag);
            Set<Integer> postIds = null;
            if(ObjectUtils.isEmpty(postIdsStr)){
                postIds = new HashSet<>();
            }
            postIds = JsonTool.fromJson(postIdsStr, new TypeReference<Set<Integer>>() {
            });
            postIds.add(discussPostId);
            postIdsStr = JsonTool.toJson(postIds);
            redisTemplate.opsForHash().put(POST_TAG, tag, postIdsStr);
        }
    }

    public Set<Integer> getDiscussPosts(Set<String> tags){
        if(ObjectUtils.isEmpty(tags)){
            return null;
        }
        Set<Integer> discussPosts = new HashSet<>();
        for(String tag : tags){
            String postIdsStr = (String) redisTemplate.opsForHash().get(POST_TAG, tag);
            if(ObjectUtils.isEmpty(postIdsStr)){
                continue;
            }
            Set<Integer> postIds = JsonTool.fromJson(postIdsStr, new TypeReference<Set<Integer>>() {
            });
            if(ObjectUtils.isEmpty(postIds)){
                continue;
            }
            discussPosts.addAll(postIds);
        }
        return discussPosts;
    }

    public BaseResponse setTag(SetTagRequest request) {
        if(ObjectUtils.isEmpty(request) || ObjectUtils.isEmpty(request.getTags())){
            return CycleErrorCode.REQUEST_MISSING.getResponse("tags");
        }
        setTag(request.getTags());
        return BaseResponse.builder().success(true).build();
    }
}
