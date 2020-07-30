package io.github.talelin.latticy.dto.blog.category;

import io.github.talelin.autoconfigure.validator.EnumValue;
import io.github.talelin.latticy.common.enumeration.OnlineStatusEnum;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 文章分类的数据传输类
 * </p>
 *
 * @author 踏雪彡寻梅
 * @version 1.0
 * @date 2020/7/30 - 21:39
 * @since JDK1.8
 */
@Data
public class CategoryDTO {

    /**
     * 文章分类权重,权重值越低,展示的位置越上
     */
    @NotNull(message = "{category.priority.not-null}")
    @Min(value = 0, message = "{category.priority.min}")
    private Integer priority;

    /**
     * 文章分类的上线状态: 1-上线,0-下线
     */
    @NotNull(message = "{online.not-null}")
    @EnumValue(target = OnlineStatusEnum.class, message = "{online.value}")
    private Integer online;

    /**
     * 文章分类名称
     */
    @NotBlank(message = "{category.name.not-blank}")
    @Length(min = 1, max = 50, message = "{category.name.length}")
    private String name;

    /**
     * 文章分类图标 url
     */
    @Length(max = 255, message = "{image.length}")
    private String icon;

}