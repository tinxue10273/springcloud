package provider.repository.impl;

import provider.domain.DiscussPostDO;
import provider.repository.DiscussPostDORepository;
import provider.repository.mapper.DiscussPostDOMapper;
import provider.vo.DiscussPostVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @authorgouhuo on 2020/04/27.
 */
@Repository
@Slf4j
public class DiscussPostDORepositoryImpl implements DiscussPostDORepository {
    @Autowired
    private DiscussPostDOMapper discussPostDOMapper;

    public int updateCommentCount(int entityId, int count) {
        try {
            return discussPostDOMapper.updateCommentCount(entityId, count);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int update(DiscussPostVO post) {
        if(!ObjectUtils.isEmpty(post.getType())){
            try {
                return discussPostDOMapper.updateType(post.getId(), post.getType());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if(!ObjectUtils.isEmpty(post.getStatus())){
            try {
                return discussPostDOMapper.updateStatus(post.getId(), post.getStatus());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if(!ObjectUtils.isEmpty(post.getScore())){
            try {
                return discussPostDOMapper.updateScore(post.getId(), post.getScore());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    public DiscussPostDO get(int id) {
        try {
            return discussPostDOMapper.selectDiscussPostById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean insert(DiscussPostDO post) {
        try {
            return discussPostDOMapper.insertDiscussPost(post) == 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public int countByUser(int userId) {
        try {
            return discussPostDOMapper.selectDiscussPostRows(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<DiscussPostDO> list(int userId, int offset, int limit, int orderMode) {
        try {
            return discussPostDOMapper.selectDiscussPosts(userId, offset, limit);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
