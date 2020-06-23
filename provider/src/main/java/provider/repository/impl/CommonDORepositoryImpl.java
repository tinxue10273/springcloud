package provider.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import provider.domain.CommentDO;
import provider.domain.CommentDOExample;
import provider.repository.CommonDORepository;
import provider.repository.mapper.CommentDOMapper;

import java.util.List;

/**
 * @authorgouhuo on 2020/04/27.
 */
@Repository
@Slf4j
public class CommonDORepositoryImpl implements CommonDORepository {

    private CommentDOMapper commentDOMapper;

    @Autowired
    public CommonDORepositoryImpl(CommentDOMapper commentDOMapper){
       this.commentDOMapper  = commentDOMapper;
    }

    public CommentDO get(int id) {
        try {
            CommentDOExample example = new CommentDOExample();
            CommentDOExample.Criteria criteria = example.createCriteria();
            criteria.andIdEqualTo(id);
            List<CommentDO> commentDOS = commentDOMapper.selectByExample(example);
            if(0 != commentDOS.size()){
                commentDOS.get(0);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int count(int entityType, int entityId) {
        try {
            CommentDOExample example = new CommentDOExample();
            CommentDOExample.Criteria criteria = example.createCriteria();
            criteria.andEntityIdEqualTo(entityId);
            criteria.andEntityTypeEqualTo(entityType);
            Long count = commentDOMapper.countByExample(example);
            return count.intValue();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int insert(CommentDO comment) {
        try {
            return commentDOMapper.insertSelective(comment);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public List<CommentDO> list(int entityType, int entityId, int offset, int limit) {
        try {
            CommentDOExample example = new CommentDOExample();
            CommentDOExample.Criteria criteria = example.createCriteria();
            criteria.andEntityTypeEqualTo(entityType);
            criteria.andEntityIdEqualTo(entityId);
            RowBounds rowBounds  = new RowBounds(offset,  limit);
            return commentDOMapper.selectByExampleWithRowbounds(example, rowBounds);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
