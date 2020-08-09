package io.github.talelin.latticy.dto.blog.tag;

import io.github.talelin.autoconfigure.validator.EnumValue;
import io.github.talelin.latticy.common.enumeration.OnlineStateEnum;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 标签的数据传输类
 * </p>
 *
 * @author 踏雪彡寻梅
 * @version 1.0
 * @date 2020/8/2 - 12:18
 * @since JDK1.8
 */
@Data
public class TagDTO {

    /**
     * 标签名称
     */
    @NotBlank(message = "{tag.name.not-blank}")
    @Length(min = 1, max = 30, message = "{tag.name.length}")
    private String name;

    /**
     * 标签的上线状态: 1-上线,0-下线
     */
    @NotNull(message = "{online.not-null}")
    @EnumValue(target = OnlineStateEnum.class, message = "{online.value}")
    private Integer online;

}