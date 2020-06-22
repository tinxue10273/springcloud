package provider.repository.impl;

import provider.domain.CommentDO;
import provider.repository.CommonDORepository;
import provider.repository.mapper.CommentDOMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @authorgouhuo on 2020/04/27.
 */
@Repository
@Slf4j
public class CommonDORepositoryImpl implements CommonDORepository {
    @Autowired
    private CommentDOMapper commentDOMapper;

    public CommentDO get(int id) {
        try {
            return commentDOMapper.selectCommentById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int count(int entityType, int entityId) {
        try {
            return commentDOMapper.selectCountByEntity(entityType, entityId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int insert(CommentDO comment) {
        try {
            return commentDOMapper.insertComment(comment);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<CommentDO> list(int entityType, int entityId, int offset, int limit) {
        try {
            return commentDOMapper.selectCommentsByEntity(entityType, entityId, offset, limit);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
