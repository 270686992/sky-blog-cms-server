package io.github.talelin.latticy.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.talelin.autoconfigure.exception.ForbiddenException;
import io.github.talelin.autoconfigure.exception.NotFoundException;
import io.github.talelin.autoconfigure.exception.ParameterException;
import io.github.talelin.latticy.common.constant.CodeMessageConstant;
import io.github.talelin.latticy.common.mybatis.Page;
import io.github.talelin.latticy.dto.blog.tag.TagDTO;
import io.github.talelin.latticy.mapper.TagMapper;
import io.github.talelin.latticy.model.TagArticleDO;
import io.github.talelin.latticy.model.TagDO;
import io.github.talelin.latticy.service.TagArticleService;
import io.github.talelin.latticy.service.TagService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 标签业务操作接口类的实现类
 * </p>
 *
 * @author 踏雪彡寻梅
 * @version 1.0
 * @date 2020-08-01 - 17:42
 * @since JDK1.8
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, TagDO> implements TagService {

    /**
     * 标签和文章间关系业务操作对象
     */
    private final TagArticleService tagArticleService;

    /**
     * 构造函数,注入该类需要的对象
     *
     * @param tagArticleService 标签和文章间关系业务操作对象
     */
    public TagServiceImpl(TagArticleService tagArticleService) {
        this.tagArticleService = tagArticleService;
    }

    @Override
    public void createTag(TagDTO tagDTO) {
        // 校验标签名称是否重复
        Integer count = this.lambdaQuery()
                .eq(TagDO::getName, tagDTO.getName())
                .count();
        if (count > 0) {
            throw new ForbiddenException(CodeMessageConstant.TAG_NAME_REPEAT);
        }

        // 添加标签
        TagDO tag = new TagDO();
        BeanUtils.copyProperties(tagDTO, tag);
        boolean saveResult = this.save(tag);
        if (!saveResult) {
            throw new ParameterException(CodeMessageConstant.CREATE_TAG_FAILED);
        }
    }

    @Override
    public TagDO getTagById(Integer tagId) {
        TagDO tag = this.getById(tagId);

        if (tag == null) {
            throw new NotFoundException(CodeMessageConstant.NOT_FOUND_TAG);
        }

        return tag;
    }

    @Override
    public void updateTagById(Integer tagId, TagDTO tagDTO) {
        // 获取相应标签
        TagDO tag = this.getTagById(tagId);

        // 校验标签名称是否重复
        Integer count = this.lambdaQuery()
                .eq(TagDO::getName, tagDTO.getName())
                .ne(TagDO::getId, tagId)
                .count();
        if (count > 0) {
            throw new ForbiddenException(CodeMessageConstant.TAG_NAME_REPEAT);
        }

        // 更新标签
        BeanUtils.copyProperties(tagDTO, tag);
        boolean updateResult = this.updateById(tag);
        if (!updateResult) {
            throw new ParameterException(CodeMessageConstant.UPDATE_TAG_FAILED);
        }
    }

    @Override
    public void deleteTagById(Integer tagId) {
        // 判断是否存在该标签
        TagDO tag = this.getById(tagId);
        if (tag == null) {
            throw new NotFoundException(CodeMessageConstant.NOT_FOUND_TAG);
        }

        // 判断标签是否和文章之间存在关联关系,如果存在则不可删除
        TagArticleDO tagArticle = this.tagArticleService
                .lambdaQuery()
                .eq(TagArticleDO::getTagId, tagId)
                .last("limit 1")
                .one();
        if (tagArticle != null) {
            throw new ForbiddenException(CodeMessageConstant.FORBIDDEN_DELETE_TAG);
        }

        // 删除标签
        int deleteResult = this.getBaseMapper().deleteById(tagId);
        if (deleteResult <= 0) {
            throw new ParameterException(CodeMessageConstant.DELETE_TAG_FAILED);
        }
    }

    @Override
    public IPage<TagDO> getTagListByPage(Integer page, Integer count) {
        // 构建分页查询对象
        Page<TagDO> pager = new Page<>(page, count);

        // 返回封装着查询结果信息的分页对象
        return this.page(pager, null);
    }

}