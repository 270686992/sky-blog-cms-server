package io.github.talelin.latticy.common.factory;

import io.github.talelin.latticy.model.EmailDO;

import java.util.Map;

/**
 * <p>
 * 邮件工厂类
 * </p>
 *
 * @author 踏雪彡寻梅
 * @version 1.0
 * @date 2020/8/7 - 14:03
 * @since JDK1.8
 */
public class EmailFactory {

    /**
     * 生成邮件信息的工厂方法
     *
     * @param recipientEmail 收件人邮箱
     * @param nickname       收件人昵称
     * @param subject        邮件主题
     * @param content        邮件内容
     * @param emailKind      邮件类型: 1-回复评论的邮件,2-回复留言的邮件
     * @return 返回生成的邮件信息对象
     */
    public static EmailDO generateEmail(String recipientEmail, String nickname, String subject, String content, Integer emailKind) {
        EmailDO email = new EmailDO();

        email.setRecipientEmail(recipientEmail)
                .setNickname(nickname)
                .setSubject(subject)
                .setContent(content)
                .setEmailKind(emailKind);

        return email;
    }

    /**
     * 生成邮件信息的工厂方法
     *
     * @param recipientEmail   收件人邮箱
     * @param nickname         收件人昵称
     * @param subject          邮件主题
     * @param content          邮件内容
     * @param emailKind        邮件类型: 1-回复评论的邮件,2-回复留言的邮件
     * @param customParameters 自定义参数(可选项)
     * @return 返回生成的邮件信息对象
     */
    public static EmailDO generateEmail(String recipientEmail, String nickname, String subject, String content, Integer emailKind, Map<String, Object> customParameters) {
        EmailDO email = new EmailDO();

        email.setRecipientEmail(recipientEmail)
                .setNickname(nickname)
                .setSubject(subject)
                .setContent(content)
                .setEmailKind(emailKind)
                .setCustomParameters(customParameters);

        return email;
    }

}