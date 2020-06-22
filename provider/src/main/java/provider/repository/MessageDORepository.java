package provider.repository;

import provider.domain.MessageDO;

import java.util.List;

/**
 * @authorgouhuo on 2020/04/27.
 */
public interface MessageDORepository {
    List<MessageDO> listNotices(int userId, String topic, int offset, int limit);

    int countUnreadNotice(int userId, String topic);

    int countNotice(int userId, String topic);

    MessageDO selectLatestNotice(int userId, String topic);

    int update(List<Integer> ids, int i);

    int add(MessageDO message);

    int countUnreadLetter(int userId, String conversationId);

    int countLetter(String conversationId);

    List<MessageDO> listLetters(String conversationId, int offset, int limit);

    int countConversation(int userId);

    List<MessageDO> listConversations(int userId, int offset, int limit);
}
