package io.github.talelin.latticy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.talelin.latticy.mapper.ArticleMapper;
import io.github.talelin.latticy.model.ArticleDO;
import io.github.talelin.latticy.service.ArticleService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 文章业务操作接口类实现类
 * </p>
 *
 * @author 踏雪彡寻梅
 * @version 1.0
 * @date 2020-07-30 - 23:13
 * @since JDK1.8
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, ArticleDO> implements ArticleService {

}