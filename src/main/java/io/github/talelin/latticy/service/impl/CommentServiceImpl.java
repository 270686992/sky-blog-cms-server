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
import io.github.talelin.latticy.common.enumeration.CommentLevelEnum;
import io.github.talelin.latticy.common.enumeration.DisplayStateEnum;
import io.github.talelin.latticy.common.enumeration.EmailKindEnum;
import io.github.talelin.latticy.common.factory.EmailFactory;
import io.github.talelin.latticy.common.mybatis.Page;
import io.github.talelin.latticy.common.util.HttpRequestProxy;
import io.github.talelin.latticy.common.util.IpParseUtil;
import io.github.talelin.latticy.dto.blog.comment.ReplyCommentDTO;
import io.github.talelin.latticy.mapper.CommentMapper;
import io.github.talelin.latticy.model.CommentDO;
import io.github.talelin.latticy.model.CustomerDO;
import io.github.talelin.latticy.model.EmailDO;
import io.github.talelin.latticy.service.CommentService;
import io.github.talelin.latticy.service.CustomerService;
import io.github.talelin.latticy.service.MailService;
import io.github.talelin.latticy.vo.UpdatedVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * 邮件服务业务操作对象
     */
    private final MailService mailService;

    /**
     * 博客用户业务操作对象
     */
    private final CustomerService customerService;

    /**
     * 站点网址首页地址
     */
    @Value("${sky-blog.home-page-url}")
    private String homePageUrl;

    /**
     * 构造函数,注入该类需要的对象
     *
     * @param mailService     邮件服务业务操作对象
     * @param customerService 博客用户业务操作对象
     */
    public CommentServiceImpl(MailService mailService, CustomerService customerService) {
        this.mailService = mailService;
        this.customerService = customerService;
    }

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

        // 获取 ip 地址并解析 ip 地址
        String ip = HttpRequestProxy.getRemoteRealIp();
        String address = IpParseUtil.getAddressByIp(ip);

        // 回复文章评论
        CommentDO reply = new CommentDO();

        reply.setArticleId(comment.getArticleId())
                .setCustomerId(0)
                .setAdminUserId(LocalUser.getLocalUser().getId())
                .setParentId(comment.getId())
                .setRoot(CommentLevelEnum.NOT_ROOT.getValue())
                .setContent(replyCommentDTO.getContent())
                .setIp(ip)
                .setAddress(address)
                .setDisplayState(DisplayStateEnum.DISPLAY.getValue());

        if (comment.getRoot().equals(CommentLevelEnum.ROOT.getValue())) {
            reply.setRootId(comment.getId());
        } else {
            reply.setRootId(comment.getRootId());
        }

        boolean saveResult = this.save(reply);
        if (!saveResult) {
            throw new ParameterException(CodeMessageConstant.REPLY_COMMENT_FAILED);
        }

        // 回复成功之后发送邮件进行通知,管理员回复管理员的评论不进行通知
        if (comment.getAdminUserId() == null) {
            // 查询回复的评论的所属用户
            CustomerDO customer = this.customerService.getCustomerById(comment.getCustomerId());
            // 封装邮件信息
            Map<String, Object> customerParameters = new HashMap<>(16);
            customerParameters.put("articleUrl", this.homePageUrl + "/article/" + comment.getArticleId());
            EmailDO email = EmailFactory.generateEmail(customer.getEmail(),
                    customer.getNickname(),
                    "博客评论回复通知",
                    reply.getContent(),
                    EmailKindEnum.REPLY_COMMENT.getValue(),
                    customerParameters);
            // 发送邮件
            this.mailService.sendInformEmail(email);
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
    @Transactional(rollbackFor = Exception.class)
    public void deleteCommentById(Integer commentId) {
        // 判断文章评论是否存在
        CommentDO comment = this.getById(commentId);
        if (comment == null) {
            throw new NotFoundException(CodeMessageConstant.NOT_FOUND_COMMENT);
        }

        if (comment.getRoot().equals(CommentLevelEnum.ROOT.getValue())) {
            // 如果为一级评论,同时删除一级评论下的二级评论
            // 如果为二级评论,只删除二级评论,不删除其子评论,删除后虽然该评论的子评论未删除,但通过 C 端前端的处理可让引用其的子评论显示的引用内容为评论已删除
            List<CommentDO> subCommentList = this.lambdaQuery()
                    .eq(CommentDO::getRoot, CommentLevelEnum.NOT_ROOT.getValue())
                    .eq(CommentDO::getRootId, comment.getId())
                    .list();

            if (CollectionUtils.isNotEmpty(subCommentList)) {
                List<Integer> subCommentIdList = new ArrayList<>();

                for (CommentDO subComment : subCommentList) {
                    subCommentIdList.add(subComment.getId());
                }

                boolean removeResult = this.removeByIds(subCommentIdList);
                if (!removeResult) {
                    throw new ParameterException(CodeMessageConstant.DELETE_COMMENT_FAILED);
                }
            }
        }

        // 删除文章评论
        int deleteResult = this.getBaseMapper().deleteById(commentId);
        if (deleteResult <= 0) {
            throw new ParameterException(CodeMessageConstant.DELETE_COMMENT_FAILED);
        }
    }

    @Override
    public IPage<CommentDO> getCommentListByPage(Integer page, Integer count, Integer isRoot) {
        // 构建分页查询对象
        Page<CommentDO> pager = new Page<>(page, count);

        // 构建查询对象
        LambdaQueryWrapper<CommentDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CommentDO::getRoot, isRoot);

        // 返回封装着查询结果信息的分页对象
        return this.page(pager, wrapper);
    }

    @Override
    public IPage<CommentDO> getSubCommentListByPage(Integer page, Integer count, Integer rootCommentId) {
        // 构建分页查询对象
        Page<CommentDO> pager = new Page<>(page, count);

        // 构建查询对象
        LambdaQueryWrapper<CommentDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CommentDO::getRoot, CommentLevelEnum.NOT_ROOT.getValue())
                .eq(CommentDO::getRootId, rootCommentId);

        // 返回封装着查询结果信息的分页对象
        return this.page(pager, wrapper);
    }

}