package io.github.talelin.latticy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.talelin.latticy.common.mybatis.Page;
import io.github.talelin.latticy.model.CustomerDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 博客用户持久化操作接口类
 * </p>
 *
 * @author 踏雪彡寻梅
 * @version 1.0
 * @date 2020-08-04 - 12:35
 * @since JDK1.8
 */
@Repository
public interface CustomerMapper extends BaseMapper<CustomerDO> {

    /**
     * 根据分页查询对象,查询关键字模糊查询当前页的博客用户列表
     *
     * @param pager   分页查询对象
     * @param keyword 查询关键字
     * @return 返回封装着模糊查询的博客用户列表的分页对象
     */
    IPage<CustomerDO> searchCustomerList(Page<CustomerDO> pager, @Param("keyword") String keyword);

}