package io.github.talelin.latticy.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.talelin.autoconfigure.exception.ForbiddenException;
import io.github.talelin.autoconfigure.exception.NotFoundException;
import io.github.talelin.autoconfigure.exception.ParameterException;
import io.github.talelin.latticy.common.LocalUser;
import io.github.talelin.latticy.common.constant.CodeMessageConstant;
import io.github.talelin.latticy.common.enumeration.DisplayStateEnum;
import io.github.talelin.latticy.common.enumeration.LeaveMessageLevelEnum;
import io.github.talelin.latticy.common.mybatis.Page;
import io.github.talelin.latticy.common.util.HttpRequestProxy;
import io.github.talelin.latticy.common.util.IpParseUtil;
import io.github.talelin.latticy.dto.blog.leavemessage.ReplyLeaveMessageDTO;
import io.github.talelin.latticy.mapper.LeaveMessageMapper;
import io.github.talelin.latticy.model.LeaveMessageDO;
import io.github.talelin.latticy.service.LeaveMessageService;
import io.github.talelin.latticy.vo.UpdatedVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

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

        // 回复留言
        LeaveMessageDO reply = new LeaveMessageDO();
        BeanUtils.copyProperties(leaveMessage, reply);

        String ip = HttpRequestProxy.getRemoteRealIp();
        String address = IpParseUtil.getAddressByIp(ip);

        if (leaveMessage.getRoot().equals(LeaveMessageLevelEnum.ROOT.getValue())) {
            // 回复一级留言
            reply.setAdminUserId(LocalUser.getLocalUser().getId())
                    .setCustomerId(0)
                    .setParentId(leaveMessage.getId())
                    .setParentCustomerId(leaveMessage.getCustomerId())
                    .setContent(replyLeaveMessageDTO.getContent())
                    .setRoot(LeaveMessageLevelEnum.NOT_ROOT.getValue())
                    .setIp(ip)
                    .setAddress(address);
        } else {
            // 回复二级留言
            reply.setAdminUserId(LocalUser.getLocalUser().getId())
                    .setCustomerId(0)
                    .setReplyId(leaveMessage.getId())
                    .setReplyCustomerId(leaveMessage.getCustomerId())
                    .setContent(replyLeaveMessageDTO.getContent())
                    .setRoot(LeaveMessageLevelEnum.NOT_ROOT.getValue())
                    .setIp(ip)
                    .setAddress(address);
        }

        boolean saveResult = this.save(reply);
        if (!saveResult) {
            throw new ParameterException(CodeMessageConstant.REPLY_LEAVE_MESSAGE_FAILED);
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
    public void deleteLeaveMessageById(Integer leaveMessageId) {
        // 判断留言是否存在
        LeaveMessageDO leaveMessage = this.getById(leaveMessageId);
        if (leaveMessage == null) {
            throw new NotFoundException(CodeMessageConstant.NOT_FOUND_LEAVE_MESSAGE);
        }

        // 删除留言
        int deleteResult = this.getBaseMapper().deleteById(leaveMessageId);
        if (deleteResult <= 0) {
            throw new ParameterException(CodeMessageConstant.DELETE_LEAVE_MESSAGE_FAILED);
        }
    }

    @Override
    public IPage<LeaveMessageDO> getLeaveMessageListByPage(Integer page, Integer count) {
        // 构建分页查询对象
        Page<LeaveMessageDO> pager = new Page<>(page, count);

        // 返回封装着查询结果信息的分页对象
        return this.page(pager, null);
    }

}