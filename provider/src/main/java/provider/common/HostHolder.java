package provider.common;

import provider.vo.UserVO;
import org.springframework.stereotype.Component;

/**
 * 持有用户信息,用于代替session对象.
 */
@Component
public class HostHolder {

    private ThreadLocal<UserVO> users = new ThreadLocal<>();

    public void setUser(UserVO user) {
        users.set(user);
    }

    public UserVO getUser() {
        return users.get();
    }

    public void clear() {
        users.remove();
    }

}
