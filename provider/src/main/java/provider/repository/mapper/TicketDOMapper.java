package provider.repository.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import provider.domain.TicketDO;
import provider.domain.TicketDOExample;

public interface TicketDOMapper {
    long countByExample(TicketDOExample example);

    int deleteByExample(TicketDOExample example);

    int insert(TicketDO record);

    int insertSelective(TicketDO record);

    List<TicketDO> selectByExampleWithRowbounds(TicketDOExample example, RowBounds rowBounds);

    List<TicketDO> selectByExample(TicketDOExample example);

    int updateByExampleSelective(@Param("record") TicketDO record, @Param("example") TicketDOExample example);

    int updateByExample(@Param("record") TicketDO record, @Param("example") TicketDOExample example);
}