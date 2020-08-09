package io.github.talelin.latticy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.talelin.latticy.model.CommentDO;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 文章评论持久化操作接口类
 * </p>
 *
 * @author 踏雪彡寻梅
 * @version 1.0
 * @date 2020-08-08 - 21:31
 * @since JDK1.8
 */
@Repository
public interface CommentMapper extends BaseMapper<CommentDO> {

}