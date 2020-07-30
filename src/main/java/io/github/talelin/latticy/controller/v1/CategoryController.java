package io.github.talelin.latticy.controller.v1;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.talelin.core.annotation.*;
import io.github.talelin.latticy.common.constant.CodeMessageConstant;
import io.github.talelin.latticy.common.util.PageUtil;
import io.github.talelin.latticy.dto.blog.category.CategoryDTO;
import io.github.talelin.latticy.model.CategoryDO;
import io.github.talelin.latticy.service.CategoryService;
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
 * 文章分类业务接口控制器
 * </p>
 *
 * @author 踏雪彡寻梅
 * @version 1.0
 * @date 2020-07-30 - 19:09
 * @since JDK1.8
 */
@RestController
@Validated
@RequestMapping("/v1/category")
@PermissionModule(value = "文章分类")
public class CategoryController extends BaseController {

    /**
     * 模块名称
     */
    private static final String MODULE_NAME = "文章分类";

    /**
     * 文章分类业务操作对象
     */
    private final CategoryService categoryService;

    /**
     * 构造函数,注入该类需要的对象
     *
     * @param categoryService 文章分类业务操作对象
     */
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * 添加一个文章分类
     *
     * @param categoryDTO 文章分类的数据传输对象
     * @return 添加成功返回成功的响应结果, 否则返回失败的响应结果
     */
    @PostMapping("")
    @PermissionMeta(value = CREATE + MODULE_NAME)
    @GroupRequired
    @Logger(template = "{user.nickname} 添加了一个文章分类")
    public CreatedVO createCategory(@RequestBody @Validated CategoryDTO categoryDTO) {
        this.categoryService.createCategory(categoryDTO);
        return new CreatedVO(CodeMessageConstant.CREATE_CATEGORY_SUCCESS);
    }

    /**
     * 根据文章分类的 ID 获取相应文章分类的信息
     *
     * @param categoryId 文章分类的 ID
     * @return 返回获取的文章分类的信息
     */
    @GetMapping("/{id}")
    @LoginRequired
    public CategoryDO getCategoryById(@PathVariable(name = "id") @Positive(message = "{id.positive}") Integer categoryId) {
        return this.categoryService.getCategoryById(categoryId);
    }

    /**
     * 根据文章分类的 ID 更新相应文章分类的信息
     *
     * @param categoryId  文章分类的 ID
     * @param categoryDTO 文章分类的数据传输对象
     * @return 更新成功返回成功的响应结果, 否则返回失败的响应结果
     */
    @PutMapping("/{id}")
    @PermissionMeta(value = UPDATE + MODULE_NAME)
    @GroupRequired
    @Logger(template = "{user.nickname} 更新了一个文章分类")
    public UpdatedVO updateCategoryById(@PathVariable(name = "id") @Positive(message = "{id.positive}") Integer categoryId,
                                        @RequestBody @Validated CategoryDTO categoryDTO) {
        this.categoryService.updateCategoryById(categoryId, categoryDTO);
        return new UpdatedVO(CodeMessageConstant.UPDATE_CATEGORY_SUCCESS);
    }

    /**
     * 根据文章分类的 ID 删除相应文章分类
     *
     * @param categoryId 文章分类的 ID
     * @return 删除成功返回成功的响应结果, 否则返回失败的响应结果
     */
    @DeleteMapping("/{id}")
    @PermissionMeta(value = DELETE + MODULE_NAME)
    @GroupRequired
    @Logger(template = "{user.nickname} 删除了一个文章分类")
    public DeletedVO deleteCategoryById(@PathVariable(name = "id") @Positive(message = "{id.positive}") Integer categoryId) {
        this.categoryService.deleteCategoryById(categoryId);
        return new DeletedVO(CodeMessageConstant.DELETE_CATEGORY_SUCCESS);
    }

    /**
     * 根据分页查询参数 page、count 获取当前页的文章分类列表
     *
     * @param page  当前页数
     * @param count 每页的文章分类数量
     * @return 返回封装着获取的文章分类列表的分页视图对象
     */
    @GetMapping("/page")
    @LoginRequired
    public PageResponseVO<CategoryDO> getCategoryListByPage(@RequestParam(name = "page", required = false, defaultValue = "0")
                                                            @Min(value = 0, message = "{page.number.min}") Integer page,
                                                            @RequestParam(name = "count", required = false, defaultValue = "10")
                                                            @Min(value = 1, message = "{page.count.min}")
                                                            @Max(value = 30, message = "{page.count.max}") Integer count) {
        // 获取封装着查询结果信息的分页对象
        IPage<CategoryDO> categoryPage = this.categoryService.getCategoryListByPage(page, count);
        // 使用分页对象构建分页视图对象并返回
        return PageUtil.build(categoryPage);
    }

    /**
     * 获取所有文章分类
     *
     * @return 返回所有文章分类
     */
    @GetMapping("/list")
    @LoginRequired
    public List<CategoryDO> getAllCategoryList() {
        return this.categoryService.list();
    }

}