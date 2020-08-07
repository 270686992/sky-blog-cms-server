package io.github.talelin.latticy.common.constant;

/**
 * <p>
 * 消息码常量类
 * </p>
 *
 * @author 踏雪彡寻梅
 * @version 1.0
 * @date 2020/7/26 - 12:26
 * @since JDK1.8
 */
public class CodeMessageConstant {

    /**
     * 通用成功消息码
     */
    public static final int OK = 0;

    /**
     * 服务器未知错误的消息码
     */
    public static final int SERVER_ERROR = 9999;



    /**
     * 添加文章分类成功的消息码
     */
    public static final int CREATE_CATEGORY_SUCCESS = 2000;

    /**
     * 更新文章分类成功的消息码
     */
    public static final int UPDATE_CATEGORY_SUCCESS = 2001;

    /**
     * 删除文章分类成功的消息码
     */
    public static final int DELETE_CATEGORY_SUCCESS = 2002;



    /**
     * 添加文章成功的消息码
     */
    public static final int CREATE_ARTICLE_SUCCESS = 2030;

    /**
     * 更新文章成功的消息码
     */
    public static final int UPDATE_ARTICLE_SUCCESS = 2031;

    /**
     * 删除文章成功的消息码
     */
    public static final int DELETE_ARTICLE_SUCCESS = 2032;



    /**
     * 添加标签成功的消息码
     */
    public static final int CREATE_TAG_SUCCESS = 2060;

    /**
     * 更新标签成功的消息码
     */
    public static final int UPDATE_TAG_SUCCESS = 2061;

    /**
     * 删除标签成功的消息码
     */
    public static final int DELETE_TAG_SUCCESS = 2062;



    /**
     * 添加友情链接成功的消息码
     */
    public static final int CREATE_FRIEND_LINK_SUCCESS = 2090;

    /**
     * 更新友情链接成功的消息码
     */
    public static final int UPDATE_FRIEND_LINK_SUCCESS = 2091;

    /**
     * 删除友情链接成功的消息码
     */
    public static final int DELETE_FRIEND_LINK_SUCCESS = 2092;



    /**
     * 更新博客用户状态成功的消息码
     */
    public static final int UPDATE_CUSTOMER_STATE_SUCCESS = 2120;



    /**
     * 添加站点信息成功的消息码
     */
    public static final int CREATE_WEB_SITE_INFO_SUCCESS = 2150;

    /**
     * 更新站点信息成功的消息码
     */
    public static final int UPDATE_WEB_SITE_INFO_SUCCESS = 2151;

    /**
     * 删除站点信息成功的消息码
     */
    public static final int DELETE_WEB_SITE_INFO_SUCCESS = 2152;



    /**
     * 找不到相关文章分类的消息码
     */
    public static final int NOT_FOUND_CATEGORY = 20000;

    /**
     * 添加文章分类失败的消息码
     */
    public static final int CREATE_CATEGORY_FAILED = 20010;

    /**
     * 更新文章分类失败的消息码
     */
    public static final int UPDATE_CATEGORY_FAILED = 20020;

    /**
     * 删除文章分类失败的消息码
     */
    public static final int DELETE_CATEGORY_FAILED = 20030;

    /**
     * 禁止删除文章分类的消息码
     */
    public static final int FORBIDDEN_DELETE_CATEGORY = 20040;

    /**
     * 文章分类名称重复的消息码
     */
    public static final int CATEGORY_NAME_REPEAT = 20050;



    /**
     * 找不到相关文章的消息码
     */
    public static final int NOT_FOUND_ARTICLE = 30000;

    /**
     * 添加文章失败的消息码
     */
    public static final int CREATE_ARTICLE_FAILED = 30010;

    /**
     * 更新文章失败的消息码
     */
    public static final int UPDATE_ARTICLE_FAILED = 30020;

    /**
     * 删除文章失败的消息码
     */
    public static final int DELETE_ARTICLE_FAILED = 30030;

    /**
     * 文章标题重复的消息码
     */
    public static final int ARTICLE_TITLE_REPEAT = 30040;

    /**
     * 文章所选标签列表存在非法信息的消息码
     */
    public static final int SELECTED_TAG_LIST_ILLEGAL = 30050;

    /**
     * 添加文章和所选标签列表间的关系失败的消息码
     */
    public static final int INSERT_TAG_ARTICLE_RELATION_FAILED = 30060;

    /**
     * 删除文章和所选标签列表间的关系失败的消息码
     */
    public static final int DELETE_TAG_ARTICLE_RELATION_FAILED = 30070;



    /**
     * 找不到相关标签的消息码
     */
    public static final int NOT_FOUND_TAG = 40000;

    /**
     * 添加标签失败的消息码
     */
    public static final int CREATE_TAG_FAILED = 40010;

    /**
     * 更新标签失败的消息码
     */
    public static final int UPDATE_TAG_FAILED = 40020;

    /**
     * 删除标签失败的消息码
     */
    public static final int DELETE_TAG_FAILED = 40030;

    /**
     * 禁止删除标签的消息码
     */
    public static final int FORBIDDEN_DELETE_TAG = 40040;

    /**
     * 标签名称重复的消息码
     */
    public static final int TAG_NAME_REPEAT = 40050;



    /**
     * 找不到相关友情链接的消息码
     */
    public static final int NOT_FOUND_FRIEND_LINK = 50000;

    /**
     * 添加友情链接失败的消息码
     */
    public static final int CREATE_FRIEND_LINK_FAILED = 50010;

    /**
     * 更新友情链接失败的消息码
     */
    public static final int UPDATE_FRIEND_LINK_FAILED = 50020;

    /**
     * 删除友情链接失败的消息码
     */
    public static final int DELETE_FRIEND_LINK_FAILED = 50030;



    /**
     * 找不到相关博客用户的消息码
     */
    public static final int NOT_FOUND_CUSTOMER = 60000;

    /**
     * 更新博客用户状态失败的消息码
     */
    public static final int UPDATE_CUSTOMER_STATE_FAILED = 60010;



    /**
     * 不支持的邮件类型的消息码
     */
    public static final int NONEXISTENT_EMAIL_KIND = 70000;



    /**
     * 找不到相关站点信息的消息码
     */
    public static final int NOT_FOUND_WEB_SITE_INFO = 80000;

    /**
     * 添加站点信息失败的消息码
     */
    public static final int CREATE_WEB_SITE_INFO_FAILED = 80010;

    /**
     * 更新站点信息失败的消息码
     */
    public static final int UPDATE_WEB_SITE_INFO_FAILED = 80020;

    /**
     * 删除站点信息失败的消息码
     */
    public static final int DELETE_WEB_SITE_INFO_FAILED = 80030;

    /**
     * 站点信息数量超过最大值的消息码
     */
    public static final int WEB_SITE_INFO_EXCEED_MAXIMUM_QUANTITY = 80040;

}