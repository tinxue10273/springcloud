package provider.repository.mapper;

import provider.domain.CommentDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentDOMapper {

    List<CommentDO> selectCommentsByEntity(@Param("entityType") int entityType, @Param("entityId") int entityId,
                                           @Param("offset") int offset, @Param("limit") int limit);

    int selectCountByEntity(@Param("entityType") int entityType, @Param("entityId") int entityId);

    int insertComment(@Param("comment") CommentDO comment);

    CommentDO selectCommentById(@Param("id") int id);
}
