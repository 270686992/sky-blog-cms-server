package io.github.talelin.latticy.controller.v1;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.talelin.core.annotation.*;
import io.github.talelin.latticy.common.constant.CodeMessageConstant;
import io.github.talelin.latticy.common.util.PageUtil;
import io.github.talelin.latticy.dto.blog.article.ArticleDTO;
import io.github.talelin.latticy.model.ArticleDO;
import io.github.talelin.latticy.model.ArticleDetailDO;
import io.github.talelin.latticy.service.ArticleService;
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
 * 文章业务接口控制器
 * </p>
 *
 * @author 踏雪彡寻梅
 * @version 1.0
 * @date 2020-07-30 - 23:13
 * @since JDK1.8
 */
@RestController
@Validated
@RequestMapping("/v1/article")
@PermissionModule(value = "文章")
public class ArticleController extends BaseController {

    /**
     * 模块名称
     */
    private static final String MODULE_NAME = "文章";

    /**
     * 文章业务操作对象
     */
    private final ArticleService articleService;

    /**
     * 构造函数,注入该类需要的对象
     *
     * @param articleService 文章业务操作对象
     */
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    /**
     * 添加一个文章
     *
     * @param articleDTO 文章的数据传输对象
     * @return 添加成功返回成功的响应结果, 否则返回失败的响应结果
     */
    @PostMapping("")
    @PermissionMeta(value = CREATE + MODULE_NAME)
    @GroupRequired
    @Logger(template = "{user.nickname} 添加了一个文章")
    public CreatedVO createArticle(@RequestBody @Validated ArticleDTO articleDTO) {
        this.articleService.createArticle(articleDTO);
        return new CreatedVO(CodeMessageConstant.CREATE_ARTICLE_SUCCESS);
    }

    /**
     * 根据文章的 ID 获取相应文章的详情信息
     *
     * @param articleId 文章的 ID
     * @return 返回获取的文章的详情信息
     */
    @GetMapping("/{id}/detail")
    @LoginRequired
    public ArticleDetailDO getArticleDetailById(@PathVariable(name = "id") @Positive(message = "{id.positive}") Integer articleId) {
        return this.articleService.getArticleDetailById(articleId);
    }

    /**
     * 根据文章的 ID 更新相应文章的信息
     *
     * @param articleId 文章的 ID
     * @param articleDTO 文章的数据传输对象
     * @return 更新成功返回成功的响应结果, 否则返回失败的响应结果
     */
    @PutMapping("/{id}")
    @PermissionMeta(value = UPDATE + MODULE_NAME)
    @GroupRequired
    @Logger(template = "{user.nickname} 更新了一个文章")
    public UpdatedVO updateArticleById(@PathVariable(name = "id") @Positive(message = "{id.positive}") Integer articleId,
                                       @RequestBody @Validated ArticleDTO articleDTO) {
        this.articleService.updateArticleById(articleId, articleDTO);
        return new UpdatedVO(CodeMessageConstant.UPDATE_ARTICLE_SUCCESS);
    }

    /**
     * 根据文章的 ID 删除相应文章
     *
     * @param articleId 文章的 ID
     * @return 删除成功返回成功的响应结果, 否则返回失败的响应结果
     */
    @DeleteMapping("/{id}")
    @PermissionMeta(value = DELETE + MODULE_NAME)
    @GroupRequired
    @Logger(template = "{user.nickname} 删除了一个文章")
    public DeletedVO deleteArticleById(@PathVariable(name = "id") @Positive(message = "{id.positive}") Integer articleId) {
        this.articleService.deleteArticleById(articleId);
        return new DeletedVO(CodeMessageConstant.DELETE_ARTICLE_SUCCESS);
    }

    /**
     * 根据分页查询参数 page、count 获取当前页的文章列表
     *
     * @param page 当前页数
     * @param count 每页的文章数量
     * @return 返回封装着获取的文章列表的分页视图对象
     */
    @GetMapping("/page")
    @LoginRequired
    public PageResponseVO<ArticleDO> getArticleListByPage(@RequestParam(name = "page", required = false, defaultValue = "0")
                                                          @Min(value = 0, message = "{page.number.min}") Integer page,
                                                          @RequestParam(name = "count", required = false, defaultValue = "10")
                                                          @Min(value = 1, message = "{page.count.min}")
                                                          @Max(value = 30, message = "{page.count.max}") Integer count) {
        // 获取封装着查询结果信息的分页对象
        IPage<ArticleDO> articlePage = this.articleService.getArticleListByPage(page, count);
        // 使用分页对象构建分页视图对象并返回
        return PageUtil.build(articlePage);
    }

}