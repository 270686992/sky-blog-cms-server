package io.github.talelin.latticy.common.enumeration;

/**
 * <p>
 * 文章发布状态枚举类
 * </p>
 *
 * @author 踏雪彡寻梅
 * @version 1.0
 * @date 2020/7/31 - 23:25
 * @since JDK1.8
 */
public enum ArticlePublishStateEnum {

    /**
     * 发布
     */
    PUBLISH(1, "发布"),

    /**
     * 私密
     */
    PRIVACY(0, "私密");

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
    ArticlePublishStateEnum(Integer value, String description) {
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