package io.github.talelin.latticy.common.enumeration;

/**
 * <p>
 * 显示状态枚举类
 * </p>
 *
 * @author 踏雪彡寻梅
 * @version 1.0
 * @date 2020/8/3 - 22:31
 * @since JDK1.8
 */
public enum DisplayStateEnum {

    /**
     * 显示
     */
    DISPLAY(1, "显示"),

    /**
     * 隐藏
     */
    HIDE(0, "隐藏");

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
    DisplayStateEnum(Integer value, String description) {
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