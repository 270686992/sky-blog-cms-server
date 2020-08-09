package io.github.talelin.latticy.controller.v1;


import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.talelin.core.annotation.*;
import io.github.talelin.latticy.common.constant.CodeMessageConstant;
import io.github.talelin.latticy.common.util.PageUtil;
import io.github.talelin.latticy.dto.blog.leavemessage.ReplyLeaveMessageDTO;
import io.github.talelin.latticy.model.LeaveMessageDO;
import io.github.talelin.latticy.service.LeaveMessageService;
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
 * 留言业务接口控制器
 * </p>
 *
 * @author 踏雪彡寻梅
 * @version 1.0
 * @date 2020-08-09 - 17:26
 * @since JDK1.8
 */
@RestController
@Validated
@RequestMapping("/v1/leave-message")
@PermissionModule(value = "留言")
public class LeaveMessageController extends BaseController {

    /**
     * 模块名称
     */
    private static final String MODULE_NAME = "留言";

    /**
     * 留言业务操作对象
     */
    private final LeaveMessageService leaveMessageService;

    /**
     * 构造函数,注入该类需要的对象
     *
     * @param leaveMessageService 留言业务操作对象
     */
    public LeaveMessageController(LeaveMessageService leaveMessageService) {
        this.leaveMessageService = leaveMessageService;
    }

    /**
     * 根据留言的 ID 获取相应留言的信息
     *
     * @param leaveMessageId 留言的 ID
     * @return 返回获取的留言的信息
     */
    @GetMapping("/{id}")
    @LoginRequired
    public LeaveMessageDO getLeaveMessageById(@PathVariable(name = "id") @Positive(message = "{id.positive}") Integer leaveMessageId) {
        return this.leaveMessageService.getLeaveMessageById(leaveMessageId);
    }

    /**
     * 根据留言的 ID 回复相应留言
     *
     * @param leaveMessageId       留言的 ID
     * @param replyLeaveMessageDTO 留言回复的数据传输对象
     * @return 回复成功返回成功的响应结果, 否则返回失败的响应结果
     */
    @PutMapping("/reply")
    @PermissionMeta(value = "回复留言")
    @GroupRequired
    @Logger(template = "{user.nickname} 回复了一个留言")
    public UpdatedVO replyLeaveMessageById(@RequestParam(name = "id") @NotNull(message = "{id.not-null}") @Positive(message = "{id.positive}") Integer leaveMessageId,
                                           @RequestBody @Validated ReplyLeaveMessageDTO replyLeaveMessageDTO) {
        this.leaveMessageService.replyLeaveMessageById(leaveMessageId, replyLeaveMessageDTO);
        return new UpdatedVO(CodeMessageConstant.REPLY_LEAVE_MESSAGE_SUCCESS);
    }

    /**
     * 根据留言的 ID 更新相应留言的显示状态
     *
     * @param leaveMessageId 留言的 ID
     * @param displayState   留言显示状态
     * @return 更新成功返回成功的响应结果, 否则返回失败的响应结果
     */
    @PutMapping("/display-state")
    @PermissionMeta(value = UPDATE + "留言显示状态")
    @GroupRequired
    @Logger(template = "{user.nickname} 更新了一个留言的显示状态")
    public UpdatedVO updateLeaveMessageDisplayStateById(@RequestParam(name = "id") @NotNull(message = "{id.not-null}") @Positive(message = "{id.positive}") Integer leaveMessageId,
                                                        @RequestParam(name = "display_state") @NotNull(message = "{display-state.not-null}") @Range(min = 0, max = 1, message = "{display-state.range}") Integer displayState) {
        return this.leaveMessageService.updateLeaveMessageDisplayStateById(leaveMessageId, displayState);
    }

    /**
     * 根据留言的 ID 删除相应留言
     *
     * @param leaveMessageId 留言的 ID
     * @return 删除成功返回成功的响应结果, 否则返回失败的响应结果
     */
    @DeleteMapping("/{id}")
    @PermissionMeta(value = DELETE + MODULE_NAME)
    @GroupRequired
    @Logger(template = "{user.nickname} 删除了一个留言")
    public DeletedVO deleteLeaveMessageById(@PathVariable(name = "id") @Positive(message = "{id.positive}") Integer leaveMessageId) {
        this.leaveMessageService.deleteLeaveMessageById(leaveMessageId);
        return new DeletedVO(CodeMessageConstant.DELETE_LEAVE_MESSAGE_SUCCESS);
    }

    /**
     * 根据分页查询参数 page、count 获取当前页的留言列表
     *
     * @param page  当前页数
     * @param count 每页的留言数量
     * @return 返回封装着获取的留言列表的分页视图对象
     */
    @GetMapping("/page")
    @LoginRequired
    public PageResponseVO<LeaveMessageDO> getLeaveMessageListByPage(@RequestParam(name = "page", required = false, defaultValue = "0")
                                                                    @Min(value = 0, message = "{page.number.min}") Integer page,
                                                                    @RequestParam(name = "count", required = false, defaultValue = "10")
                                                                    @Min(value = 1, message = "{page.count.min}")
                                                                    @Max(value = 30, message = "{page.count.max}") Integer count) {
        // 获取封装着查询结果信息的分页对象
        IPage<LeaveMessageDO> leaveMessagePage = this.leaveMessageService.getLeaveMessageListByPage(page, count);
        // 使用分页对象构建分页视图对象并返回
        return PageUtil.build(leaveMessagePage);
    }

}