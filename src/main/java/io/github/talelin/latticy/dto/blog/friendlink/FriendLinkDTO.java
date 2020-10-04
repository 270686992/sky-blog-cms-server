package io.github.talelin.latticy.dto.blog.friendlink;

import io.github.talelin.autoconfigure.validator.EnumValue;
import io.github.talelin.latticy.common.enumeration.DisplayStateEnum;
import io.github.talelin.latticy.common.enumeration.FriendLinkKindEnum;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 友情链接的数据传输类
 * </p>
 *
 * @author 踏雪彡寻梅
 * @version 1.0
 * @date 2020/8/3 - 22:22
 * @since JDK1.8
 */
@Data
public class FriendLinkDTO {

    /**
     * 友链权重,权重值越低,展示的位置越上
     */
    @NotNull(message = "{friend-link.priority.not-null}")
    @Min(value = 1, message = "{friend-link.priority.min}")
    private Integer priority;

    /**
     * 友链类型: 0-友情链接,1-推荐链接,2-站长个人链接
     */
    @NotNull(message = "{friend-link.kind.not-null}")
    @EnumValue(target = FriendLinkKindEnum.class, message = "{friend-link.kind.value}")
    private Integer kind;

    /**
     * 是否显示当前友链: 0-隐藏,1-显示
     */
    @NotNull(message = "{friend-link.display-state.not-null}")
    @EnumValue(target = DisplayStateEnum.class, message = "{friend-link.display-state.value}")
    private Integer displayState;

    /**
     * 友链名称
     */
    @NotBlank(message = "{friend-link.name.not-blank}")
    @Length(min = 1, max = 100, message = "{friend-link.name.length}")
    private String name;

    /**
     * 友链 url
     */
    @NotBlank(message = "{friend-link.url.not-blank}")
    @Length(min = 1, max = 255, message = "{friend-link.url.length}")
    @URL(message = "{friend-link.url.legal}")
    private String url;

    /**
     * 友链图标 url
     */
    @Length(max = 255, message = "{image.length}")
    private String icon;

    /**
     * 友链描述
     */
    @Length(max = 255, message = "{friend-link.description.length}")
    private String description;

}