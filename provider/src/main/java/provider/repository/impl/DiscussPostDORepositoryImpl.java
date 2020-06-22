package provider.repository.impl;

import org.apache.ibatis.session.RowBounds;
import provider.domain.DiscussPostDO;
import provider.domain.DiscussPostDOExample;
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
        int id = post.getId();
        if(!ObjectUtils.isEmpty(post.getType())){
            try {
                return discussPostDOMapper.updateType(id, post.getType());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if(!ObjectUtils.isEmpty(post.getStatus())){
            try {
                return discussPostDOMapper.updateStatus(id, post.getStatus());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if(!ObjectUtils.isEmpty(post.getScore())){
            try {
                return discussPostDOMapper.updateScore(id, post.getScore());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    public DiscussPostDO get(int id) {
        try {
            DiscussPostDOExample example = new DiscussPostDOExample();
            DiscussPostDOExample.Criteria criteria = example.createCriteria();
            criteria.andIdEqualTo(id);
            List<DiscussPostDO> discussPostDOS = discussPostDOMapper.selectByExampleWithBLOBs(example);
            if(0 != discussPostDOS.size()){
                return discussPostDOS.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean insert(DiscussPostDO post) {
        try {
            return 0 == discussPostDOMapper.insertSelective(post);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public int countByUser(int userId) {
        try {
            DiscussPostDOExample example = new DiscussPostDOExample();
            DiscussPostDOExample.Criteria criteria = example.createCriteria();
            criteria.andUserIdEqualTo(userId + "");
            Long  count = discussPostDOMapper.countByExample(example);
            return count.intValue();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public List<DiscussPostDO> list(int userId, int offset, int limit, int orderMode) {
        try {
            DiscussPostDOExample example = new DiscussPostDOExample();
            DiscussPostDOExample.Criteria criteria = example.createCriteria();
            criteria.andUserIdEqualTo(userId + "");
            return discussPostDOMapper.selectByExampleWithBLOBsWithRowbounds(example, new RowBounds(offset, limit));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
