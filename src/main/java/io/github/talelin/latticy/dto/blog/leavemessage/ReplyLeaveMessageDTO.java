package io.github.talelin.latticy.dto.blog.leavemessage;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * <p>
 * 留言回复的数据传输类
 * </p>
 *
 * @author 踏雪彡寻梅
 * @version 1.0
 * @date 2020/8/9 - 17:43
 * @since JDK1.8
 */
@Data
public class ReplyLeaveMessageDTO {

    /**
     * 回复内容
     */
    @NotBlank(message = "{leave-message.content.not-blank}")
    @Size(min = 1, max = 500, message = "{leave-message.content.length}")
    private String content;

}