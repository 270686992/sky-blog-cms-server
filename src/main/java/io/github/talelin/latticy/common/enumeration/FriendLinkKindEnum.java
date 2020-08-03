package io.github.talelin.latticy.common.enumeration;

/**
 * <p>
 * 友情链接类型枚举类
 * </p>
 *
 * @author 踏雪彡寻梅
 * @version 1.0
 * @date 2020/8/3 - 22:24
 * @since JDK1.8
 */
public enum FriendLinkKindEnum {

    /**
     * 原创
     */
    FRIEND_LINK(0, "友情链接"),

    /**
     * 推荐链接
     */
    RECOMMENDED_LINK(1, "推荐链接"),

    /**
     * 站长个人链接
     */
    PERSONAL_LINK(2, "站长个人链接");

    /**
     * 枚举值
     */
    private Integer value;

    /**
     * 枚举描述
     */
    private String description;

    /**
     * 构造函数
     *
     * @param value       枚举值
     * @param description 枚举描述
     */
    FriendLinkKindEnum(Integer value, String description) {
        this.value = value;
        this.description = description;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return this.value;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

}