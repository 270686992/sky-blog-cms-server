package io.github.talelin.latticy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import io.github.talelin.latticy.dto.blog.category.CategoryDTO;
import io.github.talelin.latticy.model.CategoryDO;

/**
 * <p>
 * 文章分类业务操作接口类
 * </p>
 *
 * @author 踏雪彡寻梅
 * @version 1.0
 * @date 2020-07-30 - 19:09
 * @since JDK1.8
 */
public interface CategoryService extends IService<CategoryDO> {

    /**
     * 添加一个文章分类
     *
     * @param categoryDTO 文章分类的数据传输对象
     */
    void createCategory(CategoryDTO categoryDTO);

    /**
     * 根据文章分类的 ID 获取相应文章分类的信息
     *
     * @param categoryId 文章分类的 ID
     * @return 返回获取的文章分类的信息
     */
    CategoryDO getCategoryById(Integer categoryId);

    /**
     * 根据文章分类的 ID 更新相应文章分类的信息
     *
     * @param categoryId  文章分类的 ID
     * @param categoryDTO 文章分类的数据传输对象
     */
    void updateCategoryById(Integer categoryId, CategoryDTO categoryDTO);

    /**
     * 根据文章分类的 ID 删除相应文章分类
     *
     * @param categoryId 文章分类的 ID
     */
    void deleteCategoryById(Integer categoryId);

    /**
     * 根据分页查询参数 page、count 获取当前页的文章分类列表
     *
     * @param page  当前页数
     * @param count 每页的文章分类数量
     * @return 返回封装着获取的文章分类列表的分页对象
     */
    IPage<CategoryDO> getCategoryListByPage(Integer page, Integer count);

}