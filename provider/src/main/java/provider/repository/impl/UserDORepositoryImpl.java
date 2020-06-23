package provider.repository.impl;

import org.springframework.util.ObjectUtils;
import provider.domain.UserDO;
import provider.domain.UserDOExample;
import provider.repository.UserDORepository;
import provider.repository.mapper.UserDOMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @authorgouhuo on 2020/04/27.
 */
@Repository
@Slf4j
public class UserDORepositoryImpl implements UserDORepository {
    private final UserDOMapper userDOMapper;

    @Autowired
    public UserDORepositoryImpl(UserDOMapper userDOMapper){
        this.userDOMapper = userDOMapper;
    }

    public UserDO get(Integer targetId) {
        try {
            UserDOExample example = new UserDOExample();
            UserDOExample.Criteria criteria =  example.createCriteria();
            criteria.andIdEqualTo(targetId);
            List<UserDO> userDOS = userDOMapper.selectByExample(example);
            if(!ObjectUtils.isEmpty(userDOS)){
                return userDOS.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public UserDO getById(int id) {
        try {
            UserDOExample example = new UserDOExample();
            UserDOExample.Criteria criteria =  example.createCriteria();
            criteria.andIdEqualTo(id);
            List<UserDO> userDOS = userDOMapper.selectByExample(example);
            if(!ObjectUtils.isEmpty(userDOS)){
                return userDOS.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public UserDO getByName(String username) {
        try {
            UserDOExample example = new UserDOExample();
            UserDOExample.Criteria criteria =  example.createCriteria();
            criteria.andUsernameEqualTo(username);
            List<UserDO> userDOS = userDOMapper.selectByExample(example);
            if(!ObjectUtils.isEmpty(userDOS)){
                return userDOS.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public UserDO getByEmail(String email) {
        try {
            UserDOExample example = new UserDOExample();
            UserDOExample.Criteria criteria =  example.createCriteria();
            criteria.andEmailEqualTo(email);
            List<UserDO> userDOS = userDOMapper.selectByExample(example);
            if(!ObjectUtils.isEmpty(userDOS)){
                return userDOS.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean add(UserDO user) {
        try {
            return 1 == userDOMapper.insertSelective(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateStatus(int userId, int i) {
        try {
            return userDOMapper.updateStatus(userId, i) == 1 ;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
