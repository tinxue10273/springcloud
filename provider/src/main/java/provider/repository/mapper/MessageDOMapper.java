package provider.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import provider.domain.MessageDO;
import provider.domain.MessageDOExample;

@Mapper
public interface MessageDOMapper {
    long countByExample(MessageDOExample example);

    int deleteByExample(MessageDOExample example);

    int insert(MessageDO record);

    int insertSelective(MessageDO record);

    List<MessageDO> selectByExampleWithBLOBsWithRowbounds(MessageDOExample example, RowBounds rowBounds);

    List<MessageDO> selectByExampleWithBLOBs(MessageDOExample example);

    List<MessageDO> selectByExampleWithRowbounds(MessageDOExample example, RowBounds rowBounds);

    List<MessageDO> selectByExample(MessageDOExample example);

    int updateByExampleSelective(@Param("record") MessageDO record, @Param("example") MessageDOExample example);

    int updateByExampleWithBLOBs(@Param("record") MessageDO record, @Param("example") MessageDOExample example);

    int updateByExample(@Param("record") MessageDO record, @Param("example") MessageDOExample example);

    // 自定义的函数
    MessageDO selectLatestNotice(@Param("userId") int userId, @Param("topic") String topic);

    int updateStatus(@Param("ids") List<Integer> ids, @Param("status") int status);

    int selectConversationCount(@Param("userId") int userId);

    List<MessageDO> selectConversations(@Param("userId") int userId, @Param("offset") int offset,
                                        @Param("limit") int limit);
}