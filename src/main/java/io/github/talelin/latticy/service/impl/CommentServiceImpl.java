package io.github.talelin.latticy.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.talelin.autoconfigure.exception.ForbiddenException;
import io.github.talelin.autoconfigure.exception.NotFoundException;
import io.github.talelin.autoconfigure.exception.ParameterException;
import io.github.talelin.latticy.common.LocalUser;
import io.github.talelin.latticy.common.constant.CodeMessageConstant;
import io.github.talelin.latticy.common.enumeration.CommentLevelEnum;
import io.github.talelin.latticy.common.enumeration.DisplayStateEnum;
import io.github.talelin.latticy.common.mybatis.Page;
import io.github.talelin.latticy.common.util.HttpRequestProxy;
import io.github.talelin.latticy.common.util.IpParseUtil;
import io.github.talelin.latticy.dto.blog.comment.ReplyCommentDTO;
import io.github.talelin.latticy.mapper.CommentMapper;
import io.github.talelin.latticy.model.CommentDO;
import io.github.talelin.latticy.service.CommentService;
import io.github.talelin.latticy.vo.UpdatedVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 文章评论业务操作接口类的实现类
 * </p>
 *
 * @author 踏雪彡寻梅
 * @version 1.0
 * @date 2020-08-08 - 21:31
 * @since JDK1.8
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, CommentDO> implements CommentService {

    @Override
    public CommentDO getCommentById(Integer commentId) {
        CommentDO comment = this.getById(commentId);

        if (comment == null) {
            throw new NotFoundException(CodeMessageConstant.NOT_FOUND_COMMENT);
        }

        return comment;
    }

    @Override
    public void replyCommentById(Integer commentId, ReplyCommentDTO replyCommentDTO) {
        // 获取要回复的文章评论
        CommentDO comment = this.getCommentById(commentId);

        // 回复文章评论
        CommentDO reply = new CommentDO();
        BeanUtils.copyProperties(comment, reply);

        String ip = HttpRequestProxy.getRemoteRealIp();
        String address = IpParseUtil.getAddressByIp(ip);

        if (comment.getRoot().equals(CommentLevelEnum.ROOT.getValue())) {
            // 回复一级评论
            reply.setAdminUserId(LocalUser.getLocalUser().getId())
                    .setCustomerId(0)
                    .setParentId(comment.getId())
                    .setParentCustomerId(comment.getCustomerId())
                    .setContent(replyCommentDTO.getContent())
                    .setRoot(CommentLevelEnum.NOT_ROOT.getValue())
                    .setIp(ip)
                    .setAddress(address);
        } else {
            // 回复二级评论
            reply.setAdminUserId(LocalUser.getLocalUser().getId())
                    .setCustomerId(0)
                    .setReplyId(comment.getId())
                    .setReplyCustomerId(comment.getCustomerId())
                    .setContent(replyCommentDTO.getContent())
                    .setRoot(CommentLevelEnum.NOT_ROOT.getValue())
                    .setIp(ip)
                    .setAddress(address);
        }

        boolean saveResult = this.save(reply);
        if (!saveResult) {
            throw new ParameterException(CodeMessageConstant.REPLY_COMMENT_FAILED);
        }
    }

    @Override
    public UpdatedVO updateCommentDisplayStateById(Integer commentId, Integer displayState) {
        // 获取相应文章评论
        CommentDO comment = this.getCommentById(commentId);

        if (comment.getDisplayState().equals(DisplayStateEnum.DISPLAY.getValue())) {
            // 显示状态更改为隐藏状态
            // 判断原状态和新状态是否一致,不一致则更新
            if (comment.getDisplayState().compareTo(displayState) != 0) {
                comment.setDisplayState(displayState);
                boolean updateResult = this.updateById(comment);
                if (updateResult) {
                    return new UpdatedVO(CodeMessageConstant.HIDE_COMMENT_SUCCESS);
                } else {
                    throw new ParameterException(CodeMessageConstant.HIDE_COMMENT_FAILED);
                }
            } else {
                throw new ForbiddenException(CodeMessageConstant.FORBIDDEN_UPDATE_COMMENT_DISPLAY_STATE);
            }
        } else {
            // 隐藏状态更改为显示状态
            // 判断原状态和新状态是否一致,不一致则更新
            if (comment.getDisplayState().compareTo(displayState) != 0) {
                comment.setDisplayState(displayState);
                boolean updateResult = this.updateById(comment);
                if (updateResult) {
                    return new UpdatedVO(CodeMessageConstant.DISPLAY_COMMENT_SUCCESS);
                } else {
                    throw new ParameterException(CodeMessageConstant.DISPLAY_COMMENT_FAILED);
                }
            } else {
                throw new ForbiddenException(CodeMessageConstant.FORBIDDEN_UPDATE_COMMENT_DISPLAY_STATE);
            }
        }
    }

    @Override
    public void deleteCommentById(Integer commentId) {
        // 判断文章评论是否存在
        CommentDO comment = this.getById(commentId);
        if (comment == null) {
            throw new NotFoundException(CodeMessageConstant.NOT_FOUND_COMMENT);
        }

        // 删除文章评论
        int deleteResult = this.getBaseMapper().deleteById(commentId);
        if (deleteResult <= 0) {
            throw new ParameterException(CodeMessageConstant.DELETE_COMMENT_FAILED);
        }
    }

    @Override
    public IPage<CommentDO> getCommentListByPage(Integer page, Integer count) {
        // 构建分页查询对象
        Page<CommentDO> pager = new Page<>(page, count);

        // 返回封装着查询结果信息的分页对象
        return this.page(pager, null);
    }

}