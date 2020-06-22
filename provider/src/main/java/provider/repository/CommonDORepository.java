package provider.repository;

import provider.domain.CommentDO;

import java.util.List;

/**
 * @authorgouhuo on 2020/04/27.
 */
public interface CommonDORepository {
    CommentDO get(int id) ;

    int count(int entityType, int entityId) ;

    int insert(CommentDO comment) ;

    List<CommentDO> list(int entityType, int entityId, int offset, int limit) ;
}
