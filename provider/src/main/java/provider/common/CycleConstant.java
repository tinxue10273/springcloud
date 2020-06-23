package provider.common;

public class CycleConstant {

    /**
     * 默认状态的登录凭证的超时时间
     */
    public static final Integer DEFAULT_EXPIRED_SECONDS = 3600 * 12;

    /**
     * 记住状态的登录凭证超时时间
     */
    public static final Integer REMEMBER_EXPIRED_SECONDS = 3600 * 24 * 100;

    /**
     * 实体类型: 帖子
     */
    public static final Integer ENTITY_TYPE_POST = 1;

    /**
     * 实体类型: 评论
     */
    public static final Integer ENTITY_TYPE_COMMENT = 2;

    /**
     * 实体类型: 用户
     */
    public static final Integer ENTITY_TYPE_USER = 3;

    /**
     * 主题: 评论
     */
    public static final String TOPIC_COMMENT = "comment";

    /**
     * 主题: 点赞
     */
    public static final String TOPIC_LIKE = "like";

    /**
     * 主题: 关注
     */
    public static final String TOPIC_FOLLOW = "follow";

    /**
     * 主题: 发帖
     */
    public static final String TOPIC_PUBLISH = "publish";

    /**
     * 主题: 删帖
     */
    public static final String TOPIC_DELETE = "delete";

    /**
     * 系统用户ID
     */
    public static final Integer SYSTEM_USER_ID = 1;

    /**
     * 权限: 普通用户
     */
    public static final String AUTHORITY_USER = "user";

    /**
     * 权限: 管理员
     */
    public static final String AUTHORITY_ADMIN = "admin";

    /**
     * 权限: 版主
     */
    public static final String AUTHORITY_MODERATOR = "moderator";

}
