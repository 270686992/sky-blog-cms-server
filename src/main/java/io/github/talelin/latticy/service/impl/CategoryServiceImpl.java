package io.github.talelin.latticy.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.talelin.autoconfigure.exception.ForbiddenException;
import io.github.talelin.autoconfigure.exception.NotFoundException;
import io.github.talelin.autoconfigure.exception.ParameterException;
import io.github.talelin.latticy.common.constant.CodeMessageConstant;
import io.github.talelin.latticy.common.mybatis.Page;
import io.github.talelin.latticy.dto.blog.category.CategoryDTO;
import io.github.talelin.latticy.mapper.CategoryMapper;
import io.github.talelin.latticy.model.CategoryDO;
import io.github.talelin.latticy.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 文章分类业务操作接口类的实现类
 * </p>
 *
 * @author 踏雪彡寻梅
 * @version 1.0
 * @date 2020-07-30 - 19:09
 * @since JDK1.8
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, CategoryDO> implements CategoryService {

    @Override
    public void createCategory(CategoryDTO categoryDTO) {
        // 校验文章分类名称是否重复
        Integer count = this.lambdaQuery()
                .eq(CategoryDO::getName, categoryDTO.getName())
                .count();
        if (count > 0) {
            throw new ForbiddenException(CodeMessageConstant.CATEGORY_NAME_REPEAT);
        }

        // 添加文章分类
        CategoryDO category = new CategoryDO();
        BeanUtils.copyProperties(categoryDTO, category);
        boolean saveResult = this.save(category);
        if (!saveResult) {
            throw new ParameterException(CodeMessageConstant.CREATE_CATEGORY_FAILED);
        }
    }

    @Override
    public CategoryDO getCategoryById(Integer categoryId) {
        CategoryDO category = this.getById(categoryId);

        if (category == null) {
            throw new NotFoundException(CodeMessageConstant.NOT_FOUND_CATEGORY);
        }

        return category;
    }

    @Override
    public void updateCategoryById(Integer categoryId, CategoryDTO categoryDTO) {
        // 获取相应文章分类
        CategoryDO category = this.getCategoryById(categoryId);

        // 校验文章分类名称是否重复
        Integer count = this.lambdaQuery()
                .eq(CategoryDO::getName, categoryDTO.getName())
                .ne(CategoryDO::getId, categoryId)
                .count();
        if (count > 0) {
            throw new ForbiddenException(CodeMessageConstant.CATEGORY_NAME_REPEAT);
        }

        // 更新文章分类
        BeanUtils.copyProperties(categoryDTO, category);
        boolean updateResult = this.updateById(category);
        if (!updateResult) {
            throw new ParameterException(CodeMessageConstant.UPDATE_CATEGORY_FAILED);
        }
    }

    @Override
    public void deleteCategoryById(Integer categoryId) {
        // 判断是否存在该文章分类
        CategoryDO category = this.getById(categoryId);
        if (category == null) {
            throw new NotFoundException(CodeMessageConstant.NOT_FOUND_CATEGORY);
        }

        // TODO 判断文章分类是否和文章之间存在关联关系,如果存在则不可删除

        // 删除文章分类
        int deleteResult = this.getBaseMapper().deleteById(categoryId);
        if (deleteResult <= 0) {
            throw new ParameterException(CodeMessageConstant.DELETE_CATEGORY_FAILED);
        }
    }

    @Override
    public IPage<CategoryDO> getCategoryListByPage(Integer page, Integer count) {
        // 构建分页查询对象
        Page<CategoryDO> pager = new Page<>(page, count);

        // 返回封装着查询结果信息的分页对象
        return this.page(pager, null);
    }

}