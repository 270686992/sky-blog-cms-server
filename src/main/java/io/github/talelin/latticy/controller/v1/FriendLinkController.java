package io.github.talelin.latticy.controller.v1;


import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.talelin.core.annotation.*;
import io.github.talelin.latticy.common.constant.CodeMessageConstant;
import io.github.talelin.latticy.common.util.PageUtil;
import io.github.talelin.latticy.dto.blog.friendlink.FriendLinkDTO;
import io.github.talelin.latticy.model.FriendLinkDO;
import io.github.talelin.latticy.service.FriendLinkService;
import io.github.talelin.latticy.vo.CreatedVO;
import io.github.talelin.latticy.vo.DeletedVO;
import io.github.talelin.latticy.vo.PageResponseVO;
import io.github.talelin.latticy.vo.UpdatedVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

/**
 * <p>
 * 友情链接业务接口控制器
 * </p>
 *
 * @author 踏雪彡寻梅
 * @version 1.0
 * @date 2020-08-03 - 22:13
 * @since JDK1.8
 */
@RestController
@Validated
@RequestMapping("/v1/friend-link")
@PermissionModule(value = "友情链接")
public class FriendLinkController extends BaseController {

    /**
     * 模块名称
     */
    private static final String MODULE_NAME = "友情链接";

    /**
     * 友情链接业务操作对象
     */
    private final FriendLinkService friendLinkService;

    /**
     * 构造函数,注入该类需要的对象
     *
     * @param friendLinkService 友情链接业务操作对象
     */
    public FriendLinkController(FriendLinkService friendLinkService) {
        this.friendLinkService = friendLinkService;
    }

    /**
     * 添加一个友情链接
     *
     * @param friendLinkDTO 友情链接的数据传输对象
     * @return 添加成功返回成功的响应结果, 否则返回失败的响应结果
     */
    @PostMapping("")
    @PermissionMeta(value = CREATE + MODULE_NAME)
    @GroupRequired
    @Logger(template = "{user.nickname} 添加了一个友情链接")
    public CreatedVO createFriendLink(@RequestBody @Validated FriendLinkDTO friendLinkDTO) {
        this.friendLinkService.createFriendLink(friendLinkDTO);
        return new CreatedVO(CodeMessageConstant.CREATE_FRIEND_LINK_SUCCESS);
    }

    /**
     * 根据友情链接的 ID 获取相应友情链接的信息
     *
     * @param friendLinkId 友情链接的 ID
     * @return 返回获取的友情链接的信息
     */
    @GetMapping("/{id}")
    @LoginRequired
    public FriendLinkDO getFriendLinkById(@PathVariable(name = "id") @Positive(message = "{id.positive}") Integer friendLinkId) {
        return this.friendLinkService.getFriendLinkById(friendLinkId);
    }

    /**
     * 根据友情链接的 ID 更新相应友情链接的信息
     *
     * @param friendLinkId  友情链接的 ID
     * @param friendLinkDTO 友情链接的数据传输对象
     * @return 更新成功返回成功的响应结果, 否则返回失败的响应结果
     */
    @PutMapping("/{id}")
    @PermissionMeta(value = UPDATE + MODULE_NAME)
    @GroupRequired
    @Logger(template = "{user.nickname} 更新了一个友情链接")
    public UpdatedVO updateFriendLinkById(@PathVariable(name = "id") @Positive(message = "{id.positive}") Integer friendLinkId,
                                          @RequestBody @Validated FriendLinkDTO friendLinkDTO) {
        this.friendLinkService.updateFriendLinkById(friendLinkId, friendLinkDTO);
        return new UpdatedVO(CodeMessageConstant.UPDATE_FRIEND_LINK_SUCCESS);
    }

    /**
     * 根据友情链接的 ID 删除相应友情链接
     *
     * @param friendLinkId 友情链接的 ID
     * @return 删除成功返回成功的响应结果, 否则返回失败的响应结果
     */
    @DeleteMapping("/{id}")
    @PermissionMeta(value = DELETE + MODULE_NAME)
    @GroupRequired
    @Logger(template = "{user.nickname} 删除了一个友情链接")
    public DeletedVO deleteFriendLinkById(@PathVariable(name = "id") @Positive(message = "{id.positive}") Integer friendLinkId) {
        this.friendLinkService.deleteFriendLinkById(friendLinkId);
        return new DeletedVO(CodeMessageConstant.DELETE_FRIEND_LINK_SUCCESS);
    }

    /**
     * 根据分页查询参数 page、count 获取当前页的友情链接列表
     *
     * @param page  当前页数
     * @param count 每页的友情链接数量
     * @return 返回封装着获取的友情链接列表的分页视图对象
     */
    @GetMapping("/page")
    @LoginRequired
    public PageResponseVO<FriendLinkDO> getFriendLinkListByPage(@RequestParam(name = "page", required = false, defaultValue = "0")
                                                                @Min(value = 0, message = "{page.number.min}") Integer page,
                                                                @RequestParam(name = "count", required = false, defaultValue = "10")
                                                                @Min(value = 1, message = "{page.count.min}")
                                                                @Max(value = 30, message = "{page.count.max}") Integer count) {
        // 获取封装着查询结果信息的分页对象
        IPage<FriendLinkDO> tagPage = this.friendLinkService.getFriendLinkListByPage(page, count);
        // 使用分页对象构建分页视图对象并返回
        return PageUtil.build(tagPage);
    }

}