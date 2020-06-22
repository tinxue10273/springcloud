package provider.repository.impl;

import org.apache.ibatis.session.RowBounds;
import org.springframework.util.ObjectUtils;
import provider.domain.MessageDO;
import provider.domain.MessageDOExample;
import provider.repository.MessageDORepository;
import provider.repository.mapper.MessageDOMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @authorgouhuo on 2020/04/27.
 */
@Repository
@Slf4j
public class MessageDORepositoryImpl implements MessageDORepository {
    @Autowired
    private MessageDOMapper messageDOMapper;

    public List<MessageDO> listNotices(int userId, String topic, int offset, int limit) {
        try {
            MessageDOExample example = new MessageDOExample();
            MessageDOExample.Criteria criteria = example.createCriteria();
            criteria.andToIdEqualTo(userId);
            criteria.andFromIdEqualTo(1);
            criteria.andStatusNotEqualTo(2);
            criteria.andConversationIdEqualTo(topic);
            example.setOrderByClause("id desc");
            return messageDOMapper.selectByExampleWithBLOBsWithRowbounds(example, new RowBounds(offset, limit));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int countUnreadNotice(int userId, String topic) {
        try {
            MessageDOExample example = new MessageDOExample();
            MessageDOExample.Criteria criteria = example.createCriteria();
            criteria.andToIdEqualTo(userId);
            criteria.andFromIdEqualTo(1);
            criteria.andStatusEqualTo(0);
            criteria.andConversationIdEqualTo(topic);
            Long count = messageDOMapper.countByExample(example);
            return count.intValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int countNotice(int userId, String topic) {
        try {
            MessageDOExample example = new MessageDOExample();
            MessageDOExample.Criteria criteria = example.createCriteria();
            criteria.andToIdEqualTo(userId);
            criteria.andFromIdEqualTo(1);
            criteria.andStatusNotEqualTo(2);
            criteria.andConversationIdEqualTo(topic);
            Long count = messageDOMapper.countByExample(example);
            return count.intValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public MessageDO selectLatestNotice(int userId, String topic) {
        try {
            return messageDOMapper.selectLatestNotice(userId, topic);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int update(List<Integer> ids, int i) {
        try {
            return messageDOMapper.updateStatus(ids, i);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int add(MessageDO message) {
        try {
            return messageDOMapper.insertSelective(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int countUnreadLetter(int userId, String conversationId) {
        try {
            MessageDOExample example = new MessageDOExample();
            MessageDOExample.Criteria criteria = example.createCriteria();
            criteria.andToIdEqualTo(userId);
            criteria.andFromIdEqualTo(1);
            criteria.andStatusEqualTo(0);
            if(!ObjectUtils.isEmpty(conversationId)){
                criteria.andToIdEqualTo(Integer.valueOf(conversationId));
            }
            Long count = messageDOMapper.countByExample(example);
            return count.intValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int countLetter(String conversationId) {
        try {
            MessageDOExample example = new MessageDOExample();
            MessageDOExample.Criteria criteria = example.createCriteria();
            criteria.andFromIdNotEqualTo(1);
            criteria.andStatusNotEqualTo(2);
            if(!ObjectUtils.isEmpty(conversationId)){
                criteria.andConversationIdEqualTo(conversationId);
            }
            Long count = messageDOMapper.countByExample(example);
            return count.intValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<MessageDO> listLetters(String conversationId, int offset, int limit) {
        try {
            MessageDOExample example = new MessageDOExample();
            MessageDOExample.Criteria criteria = example.createCriteria();
            criteria.andFromIdNotEqualTo(1);
            criteria.andStatusNotEqualTo(2);
            criteria.andConversationIdEqualTo(conversationId);
            example.setOrderByClause("id desc");
            return messageDOMapper.selectByExampleWithBLOBsWithRowbounds(example, new RowBounds(offset, limit));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int countConversation(int userId) {
        try {
            return messageDOMapper.selectConversationCount(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<MessageDO> listConversations(int userId, int offset, int limit) {
        try {
            return messageDOMapper.selectConversations(userId, offset, limit);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
