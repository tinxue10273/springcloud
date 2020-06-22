package provider.convertor;

import provider.domain.CommentDO;
import provider.vo.CommentVO;

public class CommentConvertor {

    public static CommentVO convertorVO(CommentDO comment) {
        return CommentVO.builder()
                .id(comment.getId())
                .userId(comment.getUserId())
                .entityId(comment.getEntityId())
                .entityType(comment.getEntityType())
                .targetId(comment.getTargetId())
                .content(comment.getContent())
                .createTime(comment.getCreateTime())
                .build();
    }


    public static CommentDO convertorDO(CommentVO comment) {
        return CommentDO.builder()
                .id(comment.getId())
                .userId(comment.getUserId())
                .entityId(comment.getEntityId())
                .entityType(comment.getEntityType())
                .targetId(comment.getTargetId())
                .content(comment.getContent())
                .status(0)
                .createTime(comment.getCreateTime())
                .build();
    }



}
