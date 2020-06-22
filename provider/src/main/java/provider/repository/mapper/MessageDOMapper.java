package provider.repository.mapper;

import provider.domain.MessageDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MessageDOMapper {

    List<MessageDO> selectConversations(@Param("userId") int userId, @Param("offset") int offset,
                                        @Param("limit") int limit);

    int selectConversationCount(@Param("userId") int userId);

    List<MessageDO> selectLetters(@Param("conversationId") String conversationId, @Param("offset") int offset,
                                  @Param("limit") int limit);

    int selectLetterCount(@Param("conversationId") String conversationId);

    int selectLetterUnreadCount(@Param("userId") int userId, @Param("conversationId") String conversationId);

    int insertMessage(@Param("message") MessageDO message);

    int updateStatus(@Param("ids") List<Integer> ids, @Param("status") int status);

    MessageDO selectLatestNotice(@Param("userId") int userId, @Param("topic") String topic);

    int selectNoticeCount(@Param("userId") int userId, @Param("topic") String topic);

    int selectNoticeUnreadCount(@Param("userId") int userId, @Param("topic") String topic);

    List<MessageDO> selectNotices(@Param("userId") int userId, @Param("topic") String topic,
                                  @Param("offset") int offset, @Param("limit") int limit);

}
