package io.github.talelin.latticy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.talelin.latticy.mapper.TagArticleMapper;
import io.github.talelin.latticy.model.TagArticleDO;
import io.github.talelin.latticy.service.TagArticleService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 标签和文章间关系业务操作接口类的实现类
 * </p>
 *
 * @author 踏雪彡寻梅
 * @version 1.0
 * @date 2020-08-01 - 17:22
 * @since JDK1.8
 */
@Service
public class TagArticleServiceImpl extends ServiceImpl<TagArticleMapper, TagArticleDO> implements TagArticleService {

    @Override
    public int deleteTagArticleListByArticleId(Integer articleId) {
        return this.getBaseMapper().deleteTagArticleListByArticleId(articleId);
    }

}