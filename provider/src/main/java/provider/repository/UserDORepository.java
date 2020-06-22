package provider.repository;

import provider.domain.UserDO;

/**
 * @authorgouhuo on 2020/04/27.
 */
public interface UserDORepository {
    UserDO get(Integer targetId) ;

    UserDO getById(int id);

    UserDO getByName(String username) ;

    UserDO getByEmail(String email) ;

    boolean add(UserDO user) ;

    boolean updateStatus(int userId, int i) ;
}
