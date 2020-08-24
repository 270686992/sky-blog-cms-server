package io.github.talelin.latticy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import io.github.talelin.latticy.dto.blog.article.ArticleDTO;
import io.github.talelin.latticy.model.ArticleDO;
import io.github.talelin.latticy.model.ArticleDetailDO;

/**
 * <p>
 * 文章业务操作接口类
 * </p>
 *
 * @author 踏雪彡寻梅
 * @version 1.0
 * @date 2020-07-30 - 23:13
 * @since JDK1.8
 */
public interface ArticleService extends IService<ArticleDO> {

    /**
     * 添加一个文章
     *
     * @param articleDTO 文章的数据传输对象
     */
    void createArticle(ArticleDTO articleDTO);

    /**
     * 根据文章的 ID 获取相应文章的详情信息
     *
     * @param articleId 文章的 ID
     * @return 返回获取的文章的详情信息
     */
    ArticleDetailDO getArticleDetailById(Integer articleId);

    /**
     * 根据文章的 ID 更新相应文章的信息
     *
     * @param articleId  文章的 ID
     * @param articleDTO 文章的数据传输对象
     */
    void updateArticleById(Integer articleId, ArticleDTO articleDTO);

    /**
     * 根据文章的 ID 删除相应文章
     *
     * @param articleId 文章的 ID
     */
    void deleteArticleById(Integer articleId);

    /**
     * 根据分页查询参数 page、count 获取当前页的文章列表
     *
     * @param page  当前页数
     * @param count 每页的文章数量
     * @return 返回封装着获取的文章列表的分页对象
     */
    IPage<ArticleDO> getArticleListByPage(Integer page, Integer count);

}