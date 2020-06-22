package provider.repository.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import provider.domain.DiscussPostDO;
import provider.domain.DiscussPostDOExample;

public interface DiscussPostDOMapper {
    long countByExample(DiscussPostDOExample example);

    int deleteByExample(DiscussPostDOExample example);

    int insert(DiscussPostDO record);

    int insertSelective(DiscussPostDO record);

    List<DiscussPostDO> selectByExampleWithBLOBsWithRowbounds(DiscussPostDOExample example, RowBounds rowBounds);

    List<DiscussPostDO> selectByExampleWithBLOBs(DiscussPostDOExample example);

    List<DiscussPostDO> selectByExampleWithRowbounds(DiscussPostDOExample example, RowBounds rowBounds);

    List<DiscussPostDO> selectByExample(DiscussPostDOExample example);

    int updateByExampleSelective(@Param("record") DiscussPostDO record, @Param("example") DiscussPostDOExample example);

    int updateByExampleWithBLOBs(@Param("record") DiscussPostDO record, @Param("example") DiscussPostDOExample example);

    int updateByExample(@Param("record") DiscussPostDO record, @Param("example") DiscussPostDOExample example);
}