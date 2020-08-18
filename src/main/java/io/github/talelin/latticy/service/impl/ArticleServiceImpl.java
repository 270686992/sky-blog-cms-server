package io.github.talelin.latticy.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.talelin.autoconfigure.exception.ForbiddenException;
import io.github.talelin.autoconfigure.exception.NotFoundException;
import io.github.talelin.autoconfigure.exception.ParameterException;
import io.github.talelin.latticy.common.constant.CodeMessageConstant;
import io.github.talelin.latticy.common.mybatis.Page;
import io.github.talelin.latticy.dto.blog.article.ArticleDTO;
import io.github.talelin.latticy.mapper.ArticleMapper;
import io.github.talelin.latticy.mapper.CategoryMapper;
import io.github.talelin.latticy.model.*;
import io.github.talelin.latticy.service.ArticleContentService;
import io.github.talelin.latticy.service.ArticleService;
import io.github.talelin.latticy.service.TagArticleService;
import io.github.talelin.latticy.service.TagService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 文章业务操作接口类的实现类
 * </p>
 *
 * @author 踏雪彡寻梅
 * @version 1.0
 * @date 2020-07-30 - 23:13
 * @since JDK1.8
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, ArticleDO> implements ArticleService {

    /**
     * 文章内容业务操作对象
     */
    private final ArticleContentService articleContentService;

    /**
     * 标签业务操作对象
     */
    private final TagService tagService;

    /**
     * 标签和文章间关系业务操作对象
     */
    private final TagArticleService tagArticleService;

    /**
     * 文章分类 Mapper 对象
     */
    private final CategoryMapper categoryMapper;

    /**
     * 构造函数,注入该类需要的对象
     *
     * @param articleContentService 文章内容业务操作对象
     * @param tagService            标签业务操作对象
     * @param tagArticleService     标签和文章间关系业务操作对象
     * @param categoryMapper        文章分类 Mapper 对象
     */
    public ArticleServiceImpl(ArticleContentService articleContentService, TagService tagService, TagArticleService tagArticleService, CategoryMapper categoryMapper) {
        this.articleContentService = articleContentService;
        this.tagService = tagService;
        this.tagArticleService = tagArticleService;
        this.categoryMapper = categoryMapper;
    }

    /**
     * 校验文章所选择的标签的 ID 列表是否正确并提取出相应的标签列表
     *
     * @param tagIdList 文章所选择的标签的 ID 列表
     * @return 校验成功返回相应的标签列表, 校验失败返回 null
     */
    private List<TagDO> checkTagList(List<Integer> tagIdList) {
        List<TagDO> tagList = new ArrayList<>();

        for (Integer tagId : tagIdList) {
            TagDO tag = this.tagService.getById(tagId);

            if (tag == null) {
                return null;
            }

            tagList.add(tag);
        }

        return tagList;
    }

    /**
     * 添加文章所选择的标签列表和文章之间的关联关系
     *
     * @param tagList   文章所选择的标签列表
     * @param articleId 文章的 ID
     */
    private void insertTagArticleList(List<TagDO> tagList, Integer articleId) {
        List<TagArticleDO> tagArticleList = new ArrayList<>();

        tagList.forEach(tag -> {
            TagArticleDO tagArticle = new TagArticleDO();

            tagArticle.setTagId(tag.getId())
                    .setArticleId(articleId);

            tagArticleList.add(tagArticle);
        });

        // 批量新增关联信息
        boolean saveResult = this.tagArticleService.saveBatch(tagArticleList);
        if (!saveResult) {
            throw new ParameterException(CodeMessageConstant.INSERT_TAG_ARTICLE_RELATION_FAILED);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createArticle(ArticleDTO articleDTO) {
        // 校验文章标题是否重复
        Integer count = this.lambdaQuery()
                .eq(ArticleDO::getTitle, articleDTO.getTitle())
                .count();
        if (count > 0) {
            throw new ForbiddenException(CodeMessageConstant.ARTICLE_TITLE_REPEAT);
        }

        // 添加文章
        ArticleDO article = new ArticleDO();
        BeanUtils.copyProperties(articleDTO, article);
        boolean saveResult = this.save(article);
        if (!saveResult) {
            throw new ParameterException(CodeMessageConstant.CREATE_ARTICLE_FAILED);
        }

        // 添加文章内容
        ArticleContentDO articleContent = new ArticleContentDO();
        articleContent.setArticleId(article.getId())
                .setContent(articleDTO.getContent());
        saveResult = this.articleContentService.save(articleContent);
        if (!saveResult) {
            throw new ParameterException(CodeMessageConstant.CREATE_ARTICLE_FAILED);
        }

        // 判断是否有选择标签,如有选择则校验并提取出文章所选择的标签列表,然后处理文章和所选标签之间的关联关系
        if (CollectionUtils.isNotEmpty(articleDTO.getTagIdList())) {
            List<TagDO> tagList = this.checkTagList(articleDTO.getTagIdList());
            if (tagList == null) {
                throw new ParameterException(CodeMessageConstant.SELECTED_TAG_LIST_ILLEGAL);
            }

            // 添加文章选择的标签列表和文章间的关系
            this.insertTagArticleList(tagList, article.getId());
        }
    }

    /**
     * 根据文章的 ID 获取文章
     *
     * @param articleId 文章的 ID
     * @return 返回获取的文章
     */
    private ArticleDO getArticleById(Integer articleId) {
        ArticleDO article = this.getById(articleId);

        if (article == null) {
            throw new NotFoundException(CodeMessageConstant.NOT_FOUND_ARTICLE);
        }

        return article;
    }

    /**
     * 根据文章的 ID 获取文章内容
     *
     * @param articleId 文章的 ID
     * @return 返回获取的文章内容
     */
    private ArticleContentDO getArticleContentById(Integer articleId) {
        ArticleContentDO articleContent = this.articleContentService
                .lambdaQuery()
                .eq(ArticleContentDO::getArticleId, articleId)
                .last("limit 1")
                .one();

        if (articleContent == null) {
            throw new NotFoundException(CodeMessageConstant.NOT_FOUND_ARTICLE);
        }

        return articleContent;
    }

    /**
     * 根据文章的 ID 获取其关联的标签的 ID 列表
     *
     * @param articleId 文章的 ID
     * @return 返回获取的指定文章关联的标签的 ID 列表
     */
    private List<Integer> getTagIdListByArticleId(Integer articleId) {
        List<TagArticleDO> tagArticleList = this.tagArticleService
                .lambdaQuery()
                .eq(TagArticleDO::getArticleId, articleId)
                .list();

        List<Integer> tagIdList = new ArrayList<>();

        tagArticleList.forEach(tagArticle -> tagIdList.add(tagArticle.getTagId()));

        return tagIdList;
    }

    @Override
    public ArticleDetailDO getArticleDetailById(Integer articleId) {
        // 获取文章
        ArticleDO article = this.getArticleById(articleId);

        // 获取文章内容
        ArticleContentDO articleContent = this.getArticleContentById(articleId);

        // 获取文章关联的标签的 ID 列表
        List<Integer> tagIdList = this.getTagIdListByArticleId(articleId);

        // 获取文章所属分类
        CategoryDO category = this.categoryMapper.selectById(article.getCategoryId());

        // 封装文章详情信息返回
        return new ArticleDetailDO(article, tagIdList, articleContent.getContent(), category.getName());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateArticleById(Integer articleId, ArticleDTO articleDTO) {
        // 获取文章
        ArticleDO article = this.getArticleById(articleId);

        // 获取文章内容
        ArticleContentDO articleContent = this.getArticleContentById(articleId);

        // 校验文章标题是否重复
        Integer count = this.lambdaQuery()
                .eq(ArticleDO::getTitle, articleDTO.getTitle())
                .ne(ArticleDO::getId, articleId)
                .count();
        if (count > 0) {
            throw new ForbiddenException(CodeMessageConstant.ARTICLE_TITLE_REPEAT);
        }

        // 更新文章
        BeanUtils.copyProperties(articleDTO, article);
        boolean updateResult = this.updateById(article);
        if (!updateResult) {
            throw new ParameterException(CodeMessageConstant.UPDATE_ARTICLE_FAILED);
        }

        // 更新文章内容
        articleContent.setContent(articleDTO.getContent());
        updateResult = this.articleContentService.updateById(articleContent);
        if (!updateResult) {
            throw new ParameterException(CodeMessageConstant.UPDATE_ARTICLE_FAILED);
        }

        // 判断是否有选择标签,如有选择则校验并提取出文章所选择的标签列表,然后处理文章和所选标签之间的关联关系
        if (CollectionUtils.isNotEmpty(articleDTO.getTagIdList())) {
            List<TagDO> tagList = this.checkTagList(articleDTO.getTagIdList());
            if (tagList == null) {
                throw new ParameterException(CodeMessageConstant.SELECTED_TAG_LIST_ILLEGAL);
            }

            // 判断是否存在关联关系,如存在删除原关联关系
            TagArticleDO tagArticle = this.tagArticleService
                    .lambdaQuery()
                    .eq(TagArticleDO::getArticleId, articleId)
                    .last("limit 1")
                    .one();

            if (tagArticle != null) {
                // 删除原关联关系
                int deleteResult = this.tagArticleService.deleteTagArticleListByArticleId(articleId);
                if (deleteResult <= 0) {
                    throw new ParameterException(CodeMessageConstant.DELETE_TAG_ARTICLE_RELATION_FAILED);
                }
            }

            // 添加文章新选择的标签列表和文章间的关系
            this.insertTagArticleList(tagList, article.getId());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteArticleById(Integer articleId) {
        // 获取文章
        ArticleDO article = this.getArticleById(articleId);

        // 获取文章内容
        ArticleContentDO articleContent = this.getArticleContentById(articleId);

        // 删除文章
        int deleteResult = this.getBaseMapper().deleteById(article.getId());
        if (deleteResult <= 0) {
            throw new ParameterException(CodeMessageConstant.DELETE_ARTICLE_FAILED);
        }

        // 删除文章内容
        deleteResult = this.articleContentService.getBaseMapper().deleteById(articleContent.getId());
        if (deleteResult <= 0) {
            throw new ParameterException(CodeMessageConstant.DELETE_ARTICLE_FAILED);
        }

        // 判断文章是否和标签存在关联关系,如存在删除关联关系
        TagArticleDO tagArticle = this.tagArticleService
                .lambdaQuery()
                .eq(TagArticleDO::getArticleId, articleId)
                .last("limit 1")
                .one();

        if (tagArticle != null) {
            // 删除文章和关联的标签之间的关联关系
            deleteResult = this.tagArticleService.deleteTagArticleListByArticleId(articleId);
            if (deleteResult <= 0) {
                throw new ParameterException(CodeMessageConstant.DELETE_TAG_ARTICLE_RELATION_FAILED);
            }
        }
    }

    @Override
    public IPage<ArticleDO> getArticleListByPage(Integer page, Integer count) {
        // 构建分页查询对象
        Page<ArticleDO> pager = new Page<>(page, count);

        // 返回封装着查询结果信息的分页对象
        return this.page(pager, null);
    }

}