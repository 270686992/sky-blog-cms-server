package io.github.talelin.latticy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import io.github.talelin.latticy.dto.blog.leavemessage.ReplyLeaveMessageDTO;
import io.github.talelin.latticy.model.LeaveMessageDO;
import io.github.talelin.latticy.vo.UpdatedVO;

/**
 * <p>
 * 留言业务操作接口类
 * </p>
 *
 * @author 踏雪彡寻梅
 * @version 1.0
 * @date 2020-08-09 - 17:26
 * @since JDK1.8
 */
public interface LeaveMessageService extends IService<LeaveMessageDO> {

    /**
     * 根据留言的 ID 获取相应留言的信息
     *
     * @param leaveMessageId 留言的 ID
     * @return 返回获取的留言的信息
     */
    LeaveMessageDO getLeaveMessageById(Integer leaveMessageId);

    /**
     * 根据留言的 ID 回复相应留言
     *
     * @param leaveMessageId       留言的 ID
     * @param replyLeaveMessageDTO 留言回复的数据传输对象
     */
    void replyLeaveMessageById(Integer leaveMessageId, ReplyLeaveMessageDTO replyLeaveMessageDTO);

    /**
     * 根据留言的 ID 更新相应留言的显示状态
     *
     * @param leaveMessageId 留言的 ID
     * @param displayState   留言显示状态
     * @return 更新成功返回成功的响应结果, 否则返回失败的响应结果
     */
    UpdatedVO updateLeaveMessageDisplayStateById(Integer leaveMessageId, Integer displayState);

    /**
     * 根据留言的 ID 删除相应留言
     *
     * @param leaveMessageId 留言的 ID
     */
    void deleteLeaveMessageById(Integer leaveMessageId);

    /**
     * 根据分页查询参数 page、count 获取当前页的留言列表
     *
     * @param page  当前页数
     * @param count 每页的留言数量
     * @return 返回封装着获取的留言列表的分页对象
     */
    IPage<LeaveMessageDO> getLeaveMessageListByPage(Integer page, Integer count);

}