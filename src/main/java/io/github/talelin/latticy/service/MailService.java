package io.github.talelin.latticy.service;

import io.github.talelin.latticy.model.EmailDO;

/**
 * <p>
 * 邮件服务业务接口类
 * </p>
 *
 * @author 踏雪彡寻梅
 * @version 1.0
 * @date 2020/8/7 - 11:31
 * @since JDK1.8
 */
public interface MailService {

    /**
     * 发送回复评论/回复留言的通知邮件
     *
     * @param email 邮件信息
     */
    void sendInformEmail(EmailDO email);

}