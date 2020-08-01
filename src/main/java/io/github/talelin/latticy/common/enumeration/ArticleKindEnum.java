package io.github.talelin.latticy.common.enumeration;

/**
 * <p>
 * 文章类型枚举类
 * </p>
 *
 * @author 踏雪彡寻梅
 * @version 1.0
 * @date 2020/7/31 - 23:27
 * @since JDK1.8
 */
public enum ArticleKindEnum {

    /**
     * 原创
     */
    ORIGINAL(1, "原创"),

    /**
     * 转载
     */
    REPRODUCE(0, "转载");

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
    ArticleKindEnum(Integer value, String description) {
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