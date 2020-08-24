package io.github.talelin.latticy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.talelin.autoconfigure.exception.ForbiddenException;
import io.github.talelin.autoconfigure.exception.NotFoundException;
import io.github.talelin.autoconfigure.exception.ParameterException;
import io.github.talelin.latticy.common.LocalUser;
import io.github.talelin.latticy.common.constant.CodeMessageConstant;
import io.github.talelin.latticy.common.enumeration.DisplayStateEnum;
import io.github.talelin.latticy.common.enumeration.EmailKindEnum;
import io.github.talelin.latticy.common.enumeration.LeaveMessageLevelEnum;
import io.github.talelin.latticy.common.factory.EmailFactory;
import io.github.talelin.latticy.common.mybatis.Page;
import io.github.talelin.latticy.common.util.HttpRequestProxy;
import io.github.talelin.latticy.common.util.IpParseUtil;
import io.github.talelin.latticy.dto.blog.leavemessage.ReplyLeaveMessageDTO;
import io.github.talelin.latticy.mapper.LeaveMessageMapper;
import io.github.talelin.latticy.model.CustomerDO;
import io.github.talelin.latticy.model.EmailDO;
import io.github.talelin.latticy.model.LeaveMessageDO;
import io.github.talelin.latticy.service.CustomerService;
import io.github.talelin.latticy.service.LeaveMessageService;
import io.github.talelin.latticy.service.MailService;
import io.github.talelin.latticy.vo.UpdatedVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 留言业务操作接口类的实现类
 * </p>
 *
 * @author 踏雪彡寻梅
 * @version 1.0
 * @date 2020-08-09 - 17:26
 * @since JDK1.8
 */
@Service
public class LeaveMessageServiceImpl extends ServiceImpl<LeaveMessageMapper, LeaveMessageDO> implements LeaveMessageService {

    /**
     * 邮件服务业务操作对象
     */
    private final MailService mailService;

    /**
     * 博客用户业务操作对象
     */
    private final CustomerService customerService;

    /**
     * 构造函数,注入该类需要的对象
     *
     * @param mailService     邮件服务业务操作对象
     * @param customerService 博客用户业务操作对象
     */
    public LeaveMessageServiceImpl(MailService mailService, CustomerService customerService) {
        this.mailService = mailService;
        this.customerService = customerService;
    }

    @Override
    public LeaveMessageDO getLeaveMessageById(Integer leaveMessageId) {
        LeaveMessageDO leaveMessage = this.getById(leaveMessageId);

        if (leaveMessage == null) {
            throw new NotFoundException(CodeMessageConstant.NOT_FOUND_LEAVE_MESSAGE);
        }

        return leaveMessage;
    }

    @Override
    public void replyLeaveMessageById(Integer leaveMessageId, ReplyLeaveMessageDTO replyLeaveMessageDTO) {
        // 获取要回复的留言
        LeaveMessageDO leaveMessage = this.getLeaveMessageById(leaveMessageId);

        String ip = HttpRequestProxy.getRemoteRealIp();
        String address = IpParseUtil.getAddressByIp(ip);

        // 回复留言
        LeaveMessageDO reply = new LeaveMessageDO();

        reply.setCustomerId(0)
                .setAdminUserId(LocalUser.getLocalUser().getId())
                .setParentId(leaveMessage.getId())
                .setRoot(LeaveMessageLevelEnum.NOT_ROOT.getValue())
                .setContent(replyLeaveMessageDTO.getContent())
                .setIp(ip)
                .setAddress(address)
                .setDisplayState(DisplayStateEnum.DISPLAY.getValue());

        if (leaveMessage.getRoot().equals(LeaveMessageLevelEnum.ROOT.getValue())) {
            reply.setRootId(leaveMessage.getId());
        } else {
            reply.setRootId(leaveMessage.getRootId());
        }

        boolean saveResult = this.save(reply);
        if (!saveResult) {
            throw new ParameterException(CodeMessageConstant.REPLY_LEAVE_MESSAGE_FAILED);
        }

        // 回复成功之后发送邮件进行通知,管理员回复管理员的留言不进行通知
        if (leaveMessage.getAdminUserId() == null) {
            // 查询回复的留言的所属用户
            CustomerDO customer = this.customerService.getCustomerById(leaveMessage.getCustomerId());
            // 封装邮件信息
            EmailDO email = EmailFactory.generateEmail(customer.getEmail(),
                    customer.getNickname(),
                    "博客留言回复通知",
                    reply.getContent(),
                    EmailKindEnum.REPLY_LEAVE_MESSAGE.getValue());
            // 发送邮件
            this.mailService.sendInformEmail(email);
        }
    }

    @Override
    public UpdatedVO updateLeaveMessageDisplayStateById(Integer leaveMessageId, Integer displayState) {
        // 获取相应留言
        LeaveMessageDO leaveMessage = this.getLeaveMessageById(leaveMessageId);

        if (leaveMessage.getDisplayState().equals(DisplayStateEnum.DISPLAY.getValue())) {
            // 显示状态更改为隐藏状态
            // 判断原状态和新状态是否一致,不一致则更新
            if (leaveMessage.getDisplayState().compareTo(displayState) != 0) {
                leaveMessage.setDisplayState(displayState);
                boolean updateResult = this.updateById(leaveMessage);
                if (updateResult) {
                    return new UpdatedVO(CodeMessageConstant.HIDE_LEAVE_MESSAGE_SUCCESS);
                } else {
                    throw new ParameterException(CodeMessageConstant.HIDE_LEAVE_MESSAGE_FAILED);
                }
            } else {
                throw new ForbiddenException(CodeMessageConstant.FORBIDDEN_UPDATE_LEAVE_MESSAGE_DISPLAY_STATE);
            }
        } else {
            // 隐藏状态更改为显示状态
            // 判断原状态和新状态是否一致,不一致则更新
            if (leaveMessage.getDisplayState().compareTo(displayState) != 0) {
                leaveMessage.setDisplayState(displayState);
                boolean updateResult = this.updateById(leaveMessage);
                if (updateResult) {
                    return new UpdatedVO(CodeMessageConstant.DISPLAY_LEAVE_MESSAGE_SUCCESS);
                } else {
                    throw new ParameterException(CodeMessageConstant.DISPLAY_LEAVE_MESSAGE_FAILED);
                }
            } else {
                throw new ForbiddenException(CodeMessageConstant.FORBIDDEN_UPDATE_LEAVE_MESSAGE_DISPLAY_STATE);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteLeaveMessageById(Integer leaveMessageId) {
        // 判断留言是否存在
        LeaveMessageDO leaveMessage = this.getById(leaveMessageId);
        if (leaveMessage == null) {
            throw new NotFoundException(CodeMessageConstant.NOT_FOUND_LEAVE_MESSAGE);
        }

        if (leaveMessage.getRoot().equals(LeaveMessageLevelEnum.ROOT.getValue())) {
            // 如果为一级留言,同时删除一级留言下的二级留言
            // 如果为二级留言,只删除二级留言,不删除其子留言,删除后虽然该留言的子留言未删除,但通过 C 端服务端的查询处理可以让其子留言不显示
            List<LeaveMessageDO> subLeaveMessageList = this.lambdaQuery()
                    .eq(LeaveMessageDO::getRoot, LeaveMessageLevelEnum.NOT_ROOT.getValue())
                    .eq(LeaveMessageDO::getRootId, leaveMessage.getId())
                    .list();

            if (CollectionUtils.isNotEmpty(subLeaveMessageList)) {
                List<Integer> subLeaveMessageIdList = new ArrayList<>();

                for (LeaveMessageDO subLeaveMessage : subLeaveMessageList) {
                    subLeaveMessageIdList.add(subLeaveMessage.getId());
                }

                boolean removeResult = this.removeByIds(subLeaveMessageIdList);
                if (!removeResult) {
                    throw new ParameterException(CodeMessageConstant.DELETE_COMMENT_FAILED);
                }
            }
        }

        // 删除留言
        int deleteResult = this.getBaseMapper().deleteById(leaveMessageId);
        if (deleteResult <= 0) {
            throw new ParameterException(CodeMessageConstant.DELETE_LEAVE_MESSAGE_FAILED);
        }
    }

    @Override
    public IPage<LeaveMessageDO> getLeaveMessageListByPage(Integer page, Integer count, Integer isRoot) {
        // 构建分页查询对象
        Page<LeaveMessageDO> pager = new Page<>(page, count);

        // 构建查询对象
        LambdaQueryWrapper<LeaveMessageDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LeaveMessageDO::getRoot, isRoot);

        // 返回封装着查询结果信息的分页对象
        return this.page(pager, wrapper);
    }

    @Override
    public IPage<LeaveMessageDO> getSubLeaveMessageListByPage(Integer page, Integer count, Integer rootLeaveMessageId) {
        // 构建分页查询对象
        Page<LeaveMessageDO> pager = new Page<>(page, count);

        // 构建查询对象
        LambdaQueryWrapper<LeaveMessageDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LeaveMessageDO::getRoot, LeaveMessageLevelEnum.NOT_ROOT.getValue())
                .eq(LeaveMessageDO::getRootId, rootLeaveMessageId);

        // 返回封装着查询结果信息的分页对象
        return this.page(pager, wrapper);
    }

}