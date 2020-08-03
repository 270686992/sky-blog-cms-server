package io.github.talelin.latticy.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 友情链接的数据模型类
 * </p>
 *
 * @author 踏雪彡寻梅
 * @version 1.0
 * @date 2020-08-03 - 22:13
 * @since JDK1.8
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("friend_link")
public class FriendLinkDO extends BaseModel implements Serializable {

    private static final long serialVersionUID = -3236813571736597284L;

    /**
     * 友链权重,权重值越低,展示的位置越上
     */
    private Integer priority;

    /**
     * 友链类型: 0-友情链接,1-推荐链接,2-站长个人链接
     */
    private Integer kind;

    /**
     * 是否显示当前友链: 0-隐藏,1-显示
     */
    private Integer displayState;

    /**
     * 友链名称
     */
    private String name;

    /**
     * 友链 url
     */
    private String url;

    /**
     * 友链图标 url
     */
    private String icon;

    /**
     * 友链描述
     */
    private String description;

}