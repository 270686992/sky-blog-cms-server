package io.github.talelin.latticy.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.talelin.autoconfigure.exception.NotFoundException;
import io.github.talelin.autoconfigure.exception.ParameterException;
import io.github.talelin.latticy.common.constant.CodeMessageConstant;
import io.github.talelin.latticy.common.mybatis.Page;
import io.github.talelin.latticy.mapper.CustomerMapper;
import io.github.talelin.latticy.model.CustomerDO;
import io.github.talelin.latticy.service.CustomerService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 博客用户业务操作接口类的实现类
 * </p>
 *
 * @author 踏雪彡寻梅
 * @version 1.0
 * @date 2020-08-04 - 12:35
 * @since JDK1.8
 */
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, CustomerDO> implements CustomerService {

    @Override
    public CustomerDO getCustomerById(Integer customerId) {
        CustomerDO customer = this.getById(customerId);

        if (customer == null) {
            throw new NotFoundException(CodeMessageConstant.NOT_FOUND_CUSTOMER);
        }

        return customer;
    }

    @Override
    public void updateCustomerStateById(Integer customerId, Integer customerState) {
        // 获取相应用户
        CustomerDO customer = this.getCustomerById(customerId);

        // 判断用户原状态和新状态是否一致,如果一致不更新,不一致则更新
        if (customer.getState().compareTo(customerState) != 0) {
            // 原状态和新状态不一致,进行更新
            customer.setState(customerState);
            boolean updateResult = this.updateById(customer);
            if (!updateResult) {
                throw new ParameterException(CodeMessageConstant.UPDATE_CUSTOMER_STATE_FAILED);
            }
        }
    }

    @Override
    public IPage<CustomerDO> getCustomerListByPage(Integer page, Integer count) {
        // 构建分页查询对象
        Page<CustomerDO> pager = new Page<>(page, count);

        // 返回封装着查询结果信息的分页对象
        return this.page(pager, null);
    }

    @Override
    public IPage<CustomerDO> searchCustomerList(Integer page, Integer count, String keyword) {
        // 构建分页查询对象
        Page<CustomerDO> pager = new Page<>(page, count);

        // 构建查询关键字
        if (StringUtils.isBlank(keyword)) {
            keyword = null;
        } else {
            // 根据用户名模糊查询,前面不加 % 使可以使用索引搜索
            keyword = keyword + "%";
        }

        // 返回封装着查询结果信息的分页对象
        return this.getBaseMapper().searchCustomerList(pager, keyword);
    }

}