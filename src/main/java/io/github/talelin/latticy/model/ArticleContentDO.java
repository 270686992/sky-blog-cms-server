package io.github.talelin.latticy.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 文章内容的数据模型类,与文章的关系为一对一
 * </p>
 *
 * @author 踏雪彡寻梅
 * @version 1.0
 * @date 2020-07-30 - 23:13
 * @since JDK1.8
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("article_content")
public class ArticleContentDO extends BaseModel implements Serializable {

    private static final long serialVersionUID = 2757688577654982428L;

    /**
     * 文章内容所属文章的 ID
     */
    private Integer articleId;

    /**
     * 文章内容
     */
    private String content;

}