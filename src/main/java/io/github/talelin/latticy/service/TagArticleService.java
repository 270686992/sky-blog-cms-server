package io.github.talelin.latticy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.talelin.latticy.model.TagArticleDO;

/**
 * <p>
 * 标签和文章间关系业务操作接口类
 * </p>
 *
 * @author 踏雪彡寻梅
 * @version 1.0
 * @date 2020-08-01 - 17:22
 * @since JDK1.8
 */
public interface TagArticleService extends IService<TagArticleDO> {

    /**
     * 根据文章的 ID 删除文章和其关联的标签间的关系
     *
     * @param articleId 文章的 ID
     * @return 返回删除操作影响的行数
     */
    int deleteTagArticleListByArticleId(Integer articleId);

}