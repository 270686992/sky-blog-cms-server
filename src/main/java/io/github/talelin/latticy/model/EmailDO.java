package io.github.talelin.latticy.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Map;

/**
 * <p>
 * 邮件的数据模型类
 * </p>
 *
 * @author 踏雪彡寻梅
 * @version 1.0
 * @date 2020/8/7 - 11:10
 * @since JDK1.8
 */
@Data
@Accessors(chain = true)
public class EmailDO implements Serializable {

    private static final long serialVersionUID = -2596034211472310737L;

    /**
     * 收件人邮箱
     */
    private String recipientEmail;

    /**
     * 收件人昵称
     */
    private String nickname;

    /**
     * 邮件主题
     */
    private String subject;

    /**
     * 邮件内容
     */
    private String content;

    /**
     * 邮件类型: 1-回复评论的邮件,2-回复留言的邮件
     */
    private Integer emailKind;

    /**
     * 自定义参数(非必填)
     */
    private Map<String, Object> customParameters;

}