package provider.repository.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import provider.domain.CommentDO;
import provider.domain.CommentDOExample;

public interface CommentDOMapper {
    long countByExample(CommentDOExample example);

    int deleteByExample(CommentDOExample example);

    int insert(CommentDO record);

    int insertSelective(CommentDO record);

    List<CommentDO> selectByExampleWithBLOBsWithRowbounds(CommentDOExample example, RowBounds rowBounds);

    List<CommentDO> selectByExampleWithBLOBs(CommentDOExample example);

    List<CommentDO> selectByExampleWithRowbounds(CommentDOExample example, RowBounds rowBounds);

    List<CommentDO> selectByExample(CommentDOExample example);

    int updateByExampleSelective(@Param("record") CommentDO record, @Param("example") CommentDOExample example);

    int updateByExampleWithBLOBs(@Param("record") CommentDO record, @Param("example") CommentDOExample example);

    int updateByExample(@Param("record") CommentDO record, @Param("example") CommentDOExample example);
}