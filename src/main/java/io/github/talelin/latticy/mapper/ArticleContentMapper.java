package io.github.talelin.latticy.mapper;

import io.github.talelin.latticy.model.ArticleContentDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 文章内容持久化操作接口类
 * </p>
 *
 * @author 踏雪彡寻梅
 * @version 1.0
 * @date 2020-07-30 - 23:13
 * @since JDK1.8
 */
@Repository
public interface ArticleContentMapper extends BaseMapper<ArticleContentDO> {

}