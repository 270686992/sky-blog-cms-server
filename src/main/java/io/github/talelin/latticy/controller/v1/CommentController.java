package io.github.talelin.latticy.controller.v1;


import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.talelin.core.annotation.*;
import io.github.talelin.latticy.common.constant.CodeMessageConstant;
import io.github.talelin.latticy.common.util.PageUtil;
import io.github.talelin.latticy.dto.blog.comment.ReplyCommentDTO;
import io.github.talelin.latticy.model.CommentDO;
import io.github.talelin.latticy.service.CommentService;
import io.github.talelin.latticy.vo.DeletedVO;
import io.github.talelin.latticy.vo.PageResponseVO;
import io.github.talelin.latticy.vo.UpdatedVO;
import org.hibernate.validator.constraints.Range;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * <p>
 * 文章评论业务接口控制器
 * </p>
 *
 * @author 踏雪彡寻梅
 * @version 1.0
 * @date 2020-08-08 - 21:31
 * @since JDK1.8
 */
@RestController
@Validated
@RequestMapping("/v1/comment")
@PermissionModule(value = "文章评论")
public class CommentController extends BaseController {

    /**
     * 模块名称
     */
    private static final String MODULE_NAME = "文章评论";

    /**
     * 文章评论业务操作对象
     */
    private final CommentService commentService;

    /**
     * 构造函数,注入该类需要的对象
     *
     * @param commentService 文章评论业务操作对象
     */
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    /**
     * 根据文章评论的 ID 获取相应文章评论的信息
     *
     * @param commentId 文章评论的 ID
     * @return 返回获取的文章评论的信息
     */
    @GetMapping("/{id}")
    @LoginRequired
    public CommentDO getCommentById(@PathVariable(name = "id") @Positive(message = "{id.positive}") Integer commentId) {
        return this.commentService.getCommentById(commentId);
    }

    /**
     * 根据文章评论的 ID 回复相应文章评论
     *
     * @param commentId       文章评论的 ID
     * @param replyCommentDTO 文章评论回复的数据传输对象
     * @return 回复成功返回成功的响应结果, 否则返回失败的响应结果
     */
    @PutMapping("/{id}/reply")
    @PermissionMeta(value = "回复文章评论")
    @GroupRequired
    @Logger(template = "{user.nickname} 回复了一个文章评论")
    public UpdatedVO replyCommentById(@PathVariable(name = "id") @Positive(message = "{id.positive}") Integer commentId,
                                      @RequestBody @Validated ReplyCommentDTO replyCommentDTO) {
        this.commentService.replyCommentById(commentId, replyCommentDTO);
        return new UpdatedVO(CodeMessageConstant.REPLY_COMMENT_SUCCESS);
    }

    /**
     * 根据文章评论的 ID 更新相应文章评论的显示状态
     *
     * @param commentId    文章评论的 ID
     * @param displayState 文章评论显示状态
     * @return 更新成功返回成功的响应结果, 否则返回失败的响应结果
     */
    @PutMapping("/display-state")
    @PermissionMeta(value = UPDATE + "文章评论显示状态")
    @GroupRequired
    @Logger(template = "{user.nickname} 更新了一个文章评论的显示状态")
    public UpdatedVO updateCommentDisplayStateById(@RequestParam(name = "id") @NotNull(message = "{id.not-null}") @Positive(message = "{id.positive}") Integer commentId,
                                                   @RequestParam(name = "display_state") @NotNull(message = "{display-state.not-null}") @Range(min = 0, max = 1, message = "{display-state.range}") Integer displayState) {
        return this.commentService.updateCommentDisplayStateById(commentId, displayState);
    }

    /**
     * 根据文章评论的 ID 删除相应文章评论
     *
     * @param commentId 文章评论的 ID
     * @return 删除成功返回成功的响应结果, 否则返回失败的响应结果
     */
    @DeleteMapping("/{id}")
    @PermissionMeta(value = DELETE + MODULE_NAME)
    @GroupRequired
    @Logger(template = "{user.nickname} 删除了一个文章评论")
    public DeletedVO deleteCommentById(@PathVariable(name = "id") @Positive(message = "{id.positive}") Integer commentId) {
        this.commentService.deleteCommentById(commentId);
        return new DeletedVO(CodeMessageConstant.DELETE_COMMENT_SUCCESS);
    }

    /**
     * 根据分页查询参数 page、count 获取当前页的文章评论列表
     *
     * @param page   当前页数
     * @param count  每页的文章评论数量
     * @param isRoot 标记是否获取一级评论,1: 获取一级评论,0: 获取二级评论
     * @return 返回封装着获取的文章评论列表的分页视图对象
     */
    @GetMapping("/page")
    @LoginRequired
    public PageResponseVO<CommentDO> getCommentListByPage(@RequestParam(name = "page", required = false, defaultValue = "0")
                                                          @Min(value = 0, message = "{page.number.min}") Integer page,
                                                          @RequestParam(name = "count", required = false, defaultValue = "10")
                                                          @Min(value = 1, message = "{page.count.min}")
                                                          @Max(value = 30, message = "{page.count.max}") Integer count,
                                                          @RequestParam(name = "root", required = false, defaultValue = "1")
                                                          @Range(min = 0, max = 1, message = "{comment.is-root.range}") Integer isRoot) {
        // 获取封装着查询结果信息的分页对象
        IPage<CommentDO> commentPage = this.commentService.getCommentListByPage(page, count, isRoot);
        // 使用分页对象构建分页视图对象并返回
        return PageUtil.build(commentPage);
    }

    /**
     * 根据分页查询参数 page、count 和 一级评论的 ID 获取某个一级评论下的当前页的二级评论列表
     *
     * @param page          当前页数
     * @param count         每页的二级评论数量
     * @param rootCommentId 一级评论的 ID
     * @return 返回封装着获取的二级评论列表的分页视图对象
     */
    @GetMapping("/sub-page")
    @LoginRequired
    public PageResponseVO<CommentDO> getSubCommentListByPage(@RequestParam(name = "page", required = false, defaultValue = "0")
                                                             @Min(value = 0, message = "{page.number.min}") Integer page,
                                                             @RequestParam(name = "count", required = false, defaultValue = "10")
                                                             @Min(value = 1, message = "{page.count.min}")
                                                             @Max(value = 30, message = "{page.count.max}") Integer count,
                                                             @RequestParam(name = "id") @NotNull(message = "{id.not-null}") @Positive(message = "{id.positive}") Integer rootCommentId) {
        // 获取封装着查询结果信息的分页对象
        IPage<CommentDO> commentPage = this.commentService.getSubCommentListByPage(page, count, rootCommentId);
        // 使用分页对象构建分页视图对象并返回
        return PageUtil.build(commentPage);
    }

}