package io.github.talelin.latticy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.talelin.latticy.mapper.TagMapper;
import io.github.talelin.latticy.model.TagDO;
import io.github.talelin.latticy.service.TagService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 标签业务操作接口类的实现类
 * </p>
 *
 * @author 踏雪彡寻梅
 * @version 1.0
 * @date 2020-08-01 - 17:42
 * @since JDK1.8
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, TagDO> implements TagService {

}