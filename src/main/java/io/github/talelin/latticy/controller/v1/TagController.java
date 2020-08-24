package io.github.talelin.latticy.controller.v1;


import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.talelin.core.annotation.*;
import io.github.talelin.latticy.common.constant.CodeMessageConstant;
import io.github.talelin.latticy.common.util.PageUtil;
import io.github.talelin.latticy.dto.blog.tag.TagDTO;
import io.github.talelin.latticy.model.TagDO;
import io.github.talelin.latticy.service.TagService;
import io.github.talelin.latticy.vo.CreatedVO;
import io.github.talelin.latticy.vo.DeletedVO;
import io.github.talelin.latticy.vo.PageResponseVO;
import io.github.talelin.latticy.vo.UpdatedVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.util.List;

/**
 * <p>
 * 标签业务接口控制器
 * </p>
 *
 * @author 踏雪彡寻梅
 * @version 1.0
 * @date 2020-08-01 - 17:42
 * @since JDK1.8
 */
@RestController
@Validated
@RequestMapping("/v1/tag")
@PermissionModule(value = "标签")
public class TagController extends BaseController {

    /**
     * 模块名称
     */
    private static final String MODULE_NAME = "标签";

    /**
     * 标签业务操作对象
     */
    private final TagService tagService;

    /**
     * 构造函数,注入该类需要的对象
     *
     * @param tagService 标签业务操作对象
     */
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    /**
     * 添加一个标签
     *
     * @param tagDTO 标签的数据传输对象
     * @return 添加成功返回成功的响应结果, 否则返回失败的响应结果
     */
    @PostMapping("")
    @PermissionMeta(value = CREATE + MODULE_NAME)
    @GroupRequired
    @Logger(template = "{user.nickname} 添加了一个标签")
    public CreatedVO createTag(@RequestBody @Validated TagDTO tagDTO) {
        this.tagService.createTag(tagDTO);
        return new CreatedVO(CodeMessageConstant.CREATE_TAG_SUCCESS);
    }

    /**
     * 根据标签的 ID 获取相应标签的信息
     *
     * @param tagId 标签的 ID
     * @return 返回获取的标签的信息
     */
    @GetMapping("/{id}")
    @LoginRequired
    public TagDO getTagById(@PathVariable(name = "id") @Positive(message = "{id.positive}") Integer tagId) {
        return this.tagService.getTagById(tagId);
    }

    /**
     * 根据标签的 ID 更新相应标签的信息
     *
     * @param tagId  标签的 ID
     * @param tagDTO 标签的数据传输对象
     * @return 更新成功返回成功的响应结果, 否则返回失败的响应结果
     */
    @PutMapping("/{id}")
    @PermissionMeta(value = UPDATE + MODULE_NAME)
    @GroupRequired
    @Logger(template = "{user.nickname} 更新了一个标签")
    public UpdatedVO updateTagById(@PathVariable(name = "id") @Positive(message = "{id.positive}") Integer tagId,
                                   @RequestBody @Validated TagDTO tagDTO) {
        this.tagService.updateTagById(tagId, tagDTO);
        return new UpdatedVO(CodeMessageConstant.UPDATE_TAG_SUCCESS);
    }

    /**
     * 根据标签的 ID 删除相应文章分类
     *
     * @param tagId 标签的 ID
     * @return 删除成功返回成功的响应结果, 否则返回失败的响应结果
     */
    @DeleteMapping("/{id}")
    @PermissionMeta(value = DELETE + MODULE_NAME)
    @GroupRequired
    @Logger(template = "{user.nickname} 删除了一个标签")
    public DeletedVO deleteTagById(@PathVariable(name = "id") @Positive(message = "{id.positive}") Integer tagId) {
        this.tagService.deleteTagById(tagId);
        return new DeletedVO(CodeMessageConstant.DELETE_TAG_SUCCESS);
    }

    /**
     * 根据分页查询参数 page、count 获取当前页的标签列表
     *
     * @param page  当前页数
     * @param count 每页的标签数量
     * @return 返回封装着获取的标签列表的分页视图对象
     */
    @GetMapping("/page")
    @LoginRequired
    public PageResponseVO<TagDO> getTagListByPage(@RequestParam(name = "page", required = false, defaultValue = "0")
                                                  @Min(value = 0, message = "{page.number.min}") Integer page,
                                                  @RequestParam(name = "count", required = false, defaultValue = "10")
                                                  @Min(value = 1, message = "{page.count.min}")
                                                  @Max(value = 30, message = "{page.count.max}") Integer count) {
        // 获取封装着查询结果信息的分页对象
        IPage<TagDO> tagPage = this.tagService.getTagListByPage(page, count);
        // 使用分页对象构建分页视图对象并返回
        return PageUtil.build(tagPage);
    }

    /**
     * 获取所有标签
     *
     * @return 返回所有标签
     */
    @GetMapping("/list")
    @LoginRequired
    public List<TagDO> getAllTagList() {
        return this.tagService.list();
    }

}