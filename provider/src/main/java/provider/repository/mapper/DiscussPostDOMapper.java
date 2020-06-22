package provider.repository.mapper;

import provider.domain.DiscussPostDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DiscussPostDOMapper {

    List<DiscussPostDO> selectDiscussPosts(@Param("userId") int userId, @Param("offset") int offset,
                                           @Param("limit") int limit);

    int selectDiscussPostRows(@Param("userId") int userId);

    int insertDiscussPost(@Param("DiscussPostDO") DiscussPostDO discussPost);

    DiscussPostDO selectDiscussPostById(@Param("id") int id);

    int updateCommentCount(@Param("id") int id, @Param("commentCount") int commentCount);

    int updateType(@Param("id") int id, @Param("type") int type);

    int updateStatus(@Param("id") int id, @Param("status") int status);

    int updateScore(@Param("id") int id, @Param("score") double score);

}
