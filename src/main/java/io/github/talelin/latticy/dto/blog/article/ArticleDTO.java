package io.github.talelin.latticy.dto.blog.article;

import io.github.talelin.autoconfigure.validator.EnumValue;
import io.github.talelin.latticy.common.enumeration.ArticleEnableCommentState;
import io.github.talelin.latticy.common.enumeration.ArticleKindEnum;
import io.github.talelin.latticy.common.enumeration.ArticlePublishStateEnum;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.util.List;

/**
 * <p>
 * 文章的数据传输类
 * </p>
 *
 * @author 踏雪彡寻梅
 * @version 1.0
 * @date 2020/7/31 - 23:10
 * @since JDK1.8
 */
@Data
public class ArticleDTO {

    /**
     * 文章所属分类的 ID
     */
    @NotNull(message = "{article.category-id.not-null}")
    @Positive(message = "{article.category-id.positive}")
    private Integer categoryId;

    /**
     * 文章所选的标签的 ID 列表
     */
    @Size(max = 10, message = "{article.tag.size}")
    private List<Integer> tagIdList;

    /**
     * 文章标题
     */
    @NotBlank(message = "{article.title.not-blank}")
    @Length(min = 1, max = 50, message = "{article.title.length}")
    private String title;

    /**
     * 文章简述
     */
    @Length(max = 255, message = "{article.description.length}")
    private String description;

    /**
     * 文章封面图 url
     */
    @Length(max = 255, message = "{image.length}")
    private String coverImage;

    /**
     * 文章内容
     */
    @NotBlank(message = "{article.content.not-blank}")
    @Length(min = 1, max = 40000, message = "{article.content.length}")
    private String content;

    /**
     * 文章权重,权重值越低,展示的位置越上
     */
    @NotNull(message = "{article.priority.not-null}")
    @Min(value = 0, message = "{article.priority.min}")
    private Integer priority;

    /**
     * 文章发布状态: 0-私密,1-发布
     */
    @NotNull(message = "{article.publish-state.not-null}")
    @EnumValue(target = ArticlePublishStateEnum.class, message = "{article.publish-state.value}")
    private Integer publishState;

    /**
     * 文章类型: 0-原创,1-转载
     */
    @NotNull(message = "{article.kind.not-null}")
    @EnumValue(target = ArticleKindEnum.class, message = "{article.kind.value}")
    private Integer kind;

    /**
     * 文章评论开启状态: 1-允许评论,0-不允许评论
     */
    @NotNull(message = "{article.enable-comment.not-null}")
    @EnumValue(target = ArticleEnableCommentState.class, message = "{article.enable-comment.value}")
    private Integer enableComment;

}