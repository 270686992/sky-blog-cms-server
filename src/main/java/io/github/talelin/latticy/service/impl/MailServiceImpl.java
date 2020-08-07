package io.github.talelin.latticy.service.impl;


import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import freemarker.template.Template;
import io.github.talelin.autoconfigure.exception.FailedException;
import io.github.talelin.autoconfigure.exception.ParameterException;
import io.github.talelin.latticy.common.constant.CodeMessageConstant;
import io.github.talelin.latticy.common.enumeration.EmailKindEnum;
import io.github.talelin.latticy.model.EmailDO;
import io.github.talelin.latticy.service.MailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;


/**
 * <p>
 * 邮件服务业务接口类的实现类
 * </p>
 *
 * @author 踏雪彡寻梅
 * @version 1.0
 * @date 2020/8/7 - 11:40
 * @since JDK1.8
 */
@Service
public class MailServiceImpl implements MailService {

    /**
     * 邮件服务执行对象
     */
    private final JavaMailSender mailSender;

    /**
     * FreeMarker 模板配置对象
     */
    public final FreeMarkerConfigurer freeMarkerConfigurer;

    /**
     * 发件人邮箱
     */
    @Value("${spring.mail.username}")
    public String addresserEmail;

    /**
     * 发件人昵称
     */
    @Value("${spring.mail.nickname}")
    private String addresserNickname;

    /**
     * 构造函数,注入该类需要的对象
     *
     * @param mailSender           邮件服务执行对象
     * @param freeMarkerConfigurer FreeMarker 模板配置对象
     */
    public MailServiceImpl(JavaMailSender mailSender, FreeMarkerConfigurer freeMarkerConfigurer) {
        this.mailSender = mailSender;
        this.freeMarkerConfigurer = freeMarkerConfigurer;
    }

    @Override
    public void sendInformEmail(EmailDO email) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            // 设置邮件基本信息: 发件人、收件人、邮件主题
            helper.setFrom(addresserNickname + '<' + addresserEmail + '>');
            helper.setTo(email.getRecipientEmail());
            helper.setSubject(email.getSubject());

            // 设置显示在模板中的邮件内容
            // 模板文件 template.html 处于 resources 目录下的 static/template 目录中
            Map<String, Object> model = new HashMap<>(16);
            model.put("nickname", email.getNickname());
            model.put("content", email.getContent());

            // 设置邮件模板
            Template template;
            switch (EmailKindEnum.toType(email.getEmailKind())) {
                case REPLY_COMMENT:
                    // 设置回复的评论所在的文章链接
                    if (CollectionUtils.isNotEmpty(email.getCustomParameters())) {
                        String articleUrl = (String) email.getCustomParameters().get("articleUrl");
                        if (StringUtils.isNotBlank(articleUrl)) {
                            model.put("articleUrl", articleUrl);
                        }
                    }
                    // 设置回复评论的邮件的模板
                    template = freeMarkerConfigurer.getConfiguration().getTemplate("commentInformTemplate.ftl");
                    break;
                case REPLY_LEAVE_MESSAGE:
                    // 设置回复留言的邮件的模板
                    template = freeMarkerConfigurer.getConfiguration().getTemplate("leaveMessageInformTemplate.ftl");
                    break;
                default:
                    throw new ParameterException(CodeMessageConstant.NONEXISTENT_EMAIL_KIND);
            }

            // 将显示在模板中的邮件内容整合到模板文件中
            String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

            // 设置邮件文本内容,设置为 html 形式
            helper.setText(text, true);

            // 发送邮件
            mailSender.send(message);
        } catch (Exception e) {
            throw new FailedException(CodeMessageConstant.SERVER_ERROR);
        }
    }

}