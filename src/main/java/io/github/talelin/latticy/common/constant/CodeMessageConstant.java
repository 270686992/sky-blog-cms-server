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
}