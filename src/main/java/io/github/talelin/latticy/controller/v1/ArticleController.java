package io.github.talelin.latticy.controller.v1;

import io.github.talelin.core.annotation.*;
import io.github.talelin.latticy.common.constant.CodeMessageConstant;
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

    @PostMapping("")
    @PermissionMeta(value = CREATE + MODULE_NAME)
    @GroupRequired
    @Logger(template = "{user.nickname} 添加了一个文章")
    public CreatedVO createArticle(@RequestBody @Validated ArticleDTO articleDTO) {
        return new CreatedVO(CodeMessageConstant.CREATE_ARTICLE_SUCCESS);
    }

    @GetMapping("/{id}/detail")
    @LoginRequired
    public ArticleDetailDO getArticleDetailById(@PathVariable(name = "id") @Positive(message = "{id.positive}") Integer articleId) {
        return null;
    }

    @PutMapping("/{id}")
    @PermissionMeta(value = UPDATE + MODULE_NAME)
    @GroupRequired
    @Logger(template = "{user.nickname} 更新了一个文章")
    public UpdatedVO updateArticleById(@PathVariable(name = "id") @Positive(message = "{id.positive}") Integer articleId,
                                       @RequestBody @Validated ArticleDTO articleDTO) {
        return new UpdatedVO(CodeMessageConstant.UPDATE_ARTICLE_SUCCESS);
    }

    @DeleteMapping("/{id}")
    @PermissionMeta(value = DELETE + MODULE_NAME)
    @GroupRequired
    @Logger(template = "{user.nickname} 删除了一个文章")
    public DeletedVO deleteArticleById(@PathVariable(name = "id") @Positive(message = "{id.positive}") Integer articleId) {
        return new DeletedVO(CodeMessageConstant.DELETE_ARTICLE_SUCCESS);
    }

    @GetMapping("/page")
    @LoginRequired
    public PageResponseVO<ArticleDO> getArticleListByPage(@RequestParam(name = "page", required = false, defaultValue = "0")
                                                          @Min(value = 0, message = "{page.number.min}") Integer page,
                                                          @RequestParam(name = "count", required = false, defaultValue = "10")
                                                          @Min(value = 1, message = "{page.count.min}")
                                                          @Max(value = 30, message = "{page.count.max}") Integer count) {
        return null;
    }

}