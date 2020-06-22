package provider.repository.mapper;

import provider.domain.UserDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserDOMapper {

    UserDO selectById(@Param("id") int id);

    UserDO selectByName(@Param("username") String username);

    UserDO selectByEmail(@Param("email") String email);

    int insertUser(@Param("user") UserDO user);

    int updateStatus(@Param("id") int id, @Param("status") int status);

    int updateHeader(@Param("id") int id, @Param("headerUrl") String headerUrl);

    int updatePassword(@Param("id") int id, @Param("password") String password);
}
