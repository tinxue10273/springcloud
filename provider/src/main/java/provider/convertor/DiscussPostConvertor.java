package provider.convertor;

import provider.domain.DiscussPostDO;
import provider.dto.DiscussPostDTO;
import provider.vo.DiscussPostVO;
import org.apache.commons.lang3.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

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
            return DiscussPostVO.builder()
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
        }
    }

    public static DiscussPostDO convertDO(DiscussPostVO discussPost) {
        if (ObjectUtils.isEmpty(discussPost)) {
            return null;
        } else {
            return DiscussPostDO.builder()
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
