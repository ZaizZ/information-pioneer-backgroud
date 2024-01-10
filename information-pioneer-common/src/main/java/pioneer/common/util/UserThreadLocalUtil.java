package pioneer.common.util;

import pioneer.common.dto.User;

public class UserThreadLocalUtil {
    // 定义本地线程
    public static ThreadLocal<User> tl = new ThreadLocal<>();

    /**
     * 设置本地线程中的用户
     *
     * @param user
     */
    public static void set(User user) {
        tl.set(user);
    }

    /**
     * 获取本地线程中的用户
     *
     * @return
     */
    public static User get() {
        return tl.get();
    }

    /**
     * 删除本地线程中的用户
     */
    public static void remove() {
        tl.remove();
    }
}
