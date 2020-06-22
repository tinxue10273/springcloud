package provider.repository.impl;

import provider.domain.UserDO;
import provider.repository.UserDORepository;
import provider.repository.mapper.UserDOMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @authorgouhuo on 2020/04/27.
 */
@Repository
@Slf4j
public class UserDORepositoryImpl implements UserDORepository {
    @Autowired
    private UserDOMapper userDOMapper;

    public UserDO get(Integer targetId) {
        try {
            return userDOMapper.selectById(targetId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public UserDO getById(int id) {
        try {
            return userDOMapper.selectById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public UserDO getByName(String username) {
        try {
            return userDOMapper.selectByName(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public UserDO getByEmail(String email) {
        try {
            return userDOMapper.selectByEmail(email);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean add(UserDO user) {
        try {
            return userDOMapper.insertUser(user) == 1;
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

    public boolean updateHeader(int userId, String headerUrl) {
        try {
            return userDOMapper.updateHeader(userId, headerUrl) == 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
