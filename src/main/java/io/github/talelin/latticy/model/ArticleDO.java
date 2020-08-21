package io.github.talelin.latticy.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 文章的数据模型类
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
@TableName("article")
public class ArticleDO extends BaseModel implements Serializable {

    private static final long serialVersionUID = 4322859460639794961L;

    /**
     * 文章所属分类的 ID
     */
    private Integer categoryId;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章简述
     */
    private String description;

    /**
     * 文章封面图 url
     */
    private String coverImage;

    /**
     * 文章发布状态: 0-私密,1-发布
     */
    private Integer publishState;

    /**
     * 文章阅读量
     */
    private Integer views;

    /**
     * 文章权重,权重值越低,展示的位置越上
     */
    private Integer priority;

    /**
     * 文章类型: 1-原创,0-转载
     */
    private Integer kind;

    /**
     * 文章评论数量
     */
    private Integer commentNumber;

    /**
     * 文章评论开启状态: 1-允许评论,0-不允许评论
     */
    private Integer enableComment;

}