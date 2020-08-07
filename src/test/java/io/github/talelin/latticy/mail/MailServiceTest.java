package io.github.talelin.latticy.mail;

import io.github.talelin.latticy.common.enumeration.EmailKindEnum;
import io.github.talelin.latticy.common.factory.EmailFactory;
import io.github.talelin.latticy.model.EmailDO;
import io.github.talelin.latticy.service.MailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 邮件服务测试类
 * </p>
 *
 * @author 踏雪彡寻梅
 * @version 1.0
 * @date 2020/8/7 - 12:39
 * @since JDK1.8
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MailServiceTest {

    @Autowired
    private MailService mailService;

    @Test
    public void testSendInformEmail() throws Exception {
        // 测试回复评论
        Map<String, Object> customerParameters = new HashMap<>();
        customerParameters.put("articleUrl", "https://www.xilikeli.cn/article/detail/2");

        EmailDO email1 = EmailFactory.generateEmail(
                "18289396707@163.com",
                "踏雪彡寻梅",
                "博客评论回复通知",
                "回复评论内容",
                EmailKindEnum.REPLY_COMMENT.getValue(),
                customerParameters);
        mailService.sendInformEmail(email1);

        // 测试回复留言
        EmailDO email2 = EmailFactory.generateEmail(
                "18289396707@163.com",
                "踏雪彡寻梅",
                "博客留言回复通知",
                "回复留言内容",
                EmailKindEnum.REPLY_LEAVE_MESSAGE.getValue());
        mailService.sendInformEmail(email2);
    }

}