package provider.convertor;

import com.fasterxml.jackson.core.type.TypeReference;
import provider.common.JsonTool;
import provider.domain.DiscussPostDO;
import provider.dto.DiscussPostDTO;
import provider.vo.DiscussPostVO;
import org.apache.commons.lang3.ObjectUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DiscussPostConvertor {

    public static List<DiscussPostVO> convertVOs(List<DiscussPostDO> list) {
        if (ObjectUtils.isEmpty(list)) {
            return null;
        } else {
            List<DiscussPostVO> discussPostVOS = new ArrayList<>();
            list.forEach(d -> discussPostVOS.add(convertVO(d)));
            return discussPostVOS;
        }
    }

    public static DiscussPostVO convertVO(DiscussPostDO discussPost) {
        if (ObjectUtils.isEmpty(discussPost)) {
            return null;
        } else {
            DiscussPostVO discussPostVO = DiscussPostVO.builder()
                    .id(discussPost.getId())
                    .userId(Integer.valueOf(discussPost.getUserId()))
                    .title(discussPost.getTitle())
                    .content(discussPost.getContent())
                    .type(discussPost.getType())
                    .status(discussPost.getStatus())
                    .createTime(discussPost.getCreateTime())
                    .commentCount(discussPost.getCommentCount())
                    .score(discussPost.getScore())
                    .build();
            String tagStr = discussPost.getTag();
            Set<String> tags = null;
            if(ObjectUtils.isEmpty(tagStr)){
                tags = new HashSet<>();
            }
            tags = JsonTool.fromJson(tagStr, new TypeReference<Set<String>>() {
            });
            discussPostVO.setTags(tags);
            return discussPostVO;
        }
    }

    public static DiscussPostDO convertDO(DiscussPostVO discussPost) {
        if (ObjectUtils.isEmpty(discussPost)) {
            return null;
        } else {
            DiscussPostDO discussPostDO = DiscussPostDO.builder()
                    .id(discussPost.getId())
                    .userId(discussPost.getUserId() + "")
                    .title(discussPost.getTitle())
                    .content(discussPost.getContent())
                    .type(discussPost.getType())
                    .createTime(discussPost.getCreateTime())
                    .commentCount(discussPost.getCommentCount())
                    .status(0)
                    .score(discussPost.getScore())
                    .build();
            Set<String> tags = discussPost.getTags();
            if(!ObjectUtils.isEmpty(tags)){
                discussPostDO.setTag(JsonTool.toJson(tags));
            }
            return discussPostDO;
        }
    }


    public static DiscussPostDTO convertDTO(DiscussPostVO discussPost) {
        if (ObjectUtils.isEmpty(discussPost)) {
            return null;
        } else {
            return DiscussPostDTO.builder()
                    .id(discussPost.getId())
                    .userId(discussPost.getUserId())
                    .title(discussPost.getTitle())
                    .content(discussPost.getContent())
                    .type(discussPost.getType())
                    .createTime(discussPost.getCreateTime())
                    .commentCount(discussPost.getCommentCount())
                    .status(0)
                    .score(discussPost.getScore())
                    .build();
        }
    }

}
