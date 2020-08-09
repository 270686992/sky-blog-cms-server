package io.github.talelin.latticy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import io.github.talelin.latticy.dto.blog.comment.ReplyCommentDTO;
import io.github.talelin.latticy.model.CommentDO;
import io.github.talelin.latticy.vo.UpdatedVO;

/**
 * <p>
 * 文章评论业务操作接口类
 * </p>
 *
 * @author 踏雪彡寻梅
 * @version 1.0
 * @date 2020-08-08 - 21:31
 * @since JDK1.8
 */
public interface CommentService extends IService<CommentDO> {

    /**
     * 根据文章评论的 ID 获取相应文章评论的信息
     *
     * @param commentId 文章评论的 ID
     * @return 返回获取的文章评论的信息
     */
    CommentDO getCommentById(Integer commentId);

    /**
     * 根据文章评论的 ID 回复相应文章评论
     *
     * @param commentId       文章评论的 ID
     * @param replyCommentDTO 文章评论回复的数据传输对象
     */
    void replyCommentById(Integer commentId, ReplyCommentDTO replyCommentDTO);

    /**
     * 根据文章评论的 ID 更新相应文章评论的显示状态
     *
     * @param commentId    文章评论的 ID
     * @param displayState 文章评论显示状态
     * @return 更新成功返回成功的响应结果, 否则返回失败的响应结果
     */
    UpdatedVO updateCommentDisplayStateById(Integer commentId, Integer displayState);

    /**
     * 根据文章评论的 ID 删除相应文章评论
     *
     * @param commentId 文章评论的 ID
     */
    void deleteCommentById(Integer commentId);

    /**
     * 根据分页查询参数 page、count 获取当前页的文章评论列表
     *
     * @param page  当前页数
     * @param count 每页的文章评论数量
     * @return 返回封装着获取的文章评论列表的分页对象
     */
    IPage<CommentDO> getCommentListByPage(Integer page, Integer count);

}