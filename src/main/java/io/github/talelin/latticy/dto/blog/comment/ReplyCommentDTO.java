package io.github.talelin.latticy.dto.blog.comment;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * <p>
 * 文章评论回复的数据传输类
 * </p>
 *
 * @author 踏雪彡寻梅
 * @version 1.0
 * @date 2020/8/8 - 22:11
 * @since JDK1.8
 */
@Data
public class ReplyCommentDTO {

    /**
     * 回复内容
     */
    @NotBlank(message = "{comment.content.not-blank}")
    @Size(min = 1, max = 500, message = "{comment.content.length}")
    private String content;

}