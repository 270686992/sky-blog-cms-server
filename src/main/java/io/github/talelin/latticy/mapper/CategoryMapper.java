package io.github.talelin.latticy.mapper;

import io.github.talelin.latticy.model.CategoryDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 文章分类持久化操作接口类
 * </p>
 *
 * @author 踏雪彡寻梅
 * @version 1.0
 * @date 2020-07-30 - 19:09
 * @since JDK1.8
 */
@Repository
public interface CategoryMapper extends BaseMapper<CategoryDO> {

}