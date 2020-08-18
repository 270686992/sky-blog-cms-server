package io.github.talelin.latticy.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 标签和文章间关系的数据模型类
 * </p>
 *
 * @author 踏雪彡寻梅
 * @version 1.0
 * @date 2020-08-01 - 17:22
 * @since JDK1.8
 */
@Data
@Accessors(chain = true)
@TableName("tag_article")
public class TagArticleDO implements Serializable {

    private static final long serialVersionUID = -6408794647627712271L;

    /**
     * 标签和文章间关系的 ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 标签 ID
     */
    private Integer tagId;

    /**
     * 文章 ID
     */
    private Integer articleId;

}