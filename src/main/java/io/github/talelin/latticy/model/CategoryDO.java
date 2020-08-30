package io.github.talelin.latticy.model;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 文章分类的数据模型类
 * </p>
 *
 * @author 踏雪彡寻梅
 * @version 1.0
 * @date 2020-07-30 - 19:09
 * @since JDK1.8
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("category")
public class CategoryDO extends BaseModel implements Serializable {

    private static final long serialVersionUID = -5383162761230057839L;

    /**
     * 文章分类权重,权重值越低,展示的位置越上
     */
    private Integer priority;

    /**
     * 文章分类的上线状态: 1-上线,0-下线
     */
    private Integer online;

    /**
     * 文章分类名称
     */
    private String name;

    /**
     * 文章分类描述
     */
    private String description;

    /**
     * 文章分类图标 url
     */
    private String icon;

}