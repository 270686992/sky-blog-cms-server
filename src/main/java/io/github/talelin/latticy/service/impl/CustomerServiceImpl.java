package io.github.talelin.latticy.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.talelin.autoconfigure.exception.ForbiddenException;
import io.github.talelin.autoconfigure.exception.NotFoundException;
import io.github.talelin.autoconfigure.exception.ParameterException;
import io.github.talelin.latticy.common.constant.CodeMessageConstant;
import io.github.talelin.latticy.common.enumeration.CustomerStateEnum;
import io.github.talelin.latticy.common.mybatis.Page;
import io.github.talelin.latticy.common.util.SensitiveDataUtil;
import io.github.talelin.latticy.mapper.CustomerMapper;
import io.github.talelin.latticy.model.CustomerDO;
import io.github.talelin.latticy.service.CustomerService;
import io.github.talelin.latticy.vo.UpdatedVO;
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

        // 数据加密显示
        customer.setEmail(SensitiveDataUtil.emailHide(customer.getEmail()));
        customer.setUsername(SensitiveDataUtil.defaultHide(customer.getUsername()));

        return customer;
    }

    @Override
    public UpdatedVO updateCustomerStateById(Integer customerId, Integer customerState) {
        // 获取相应用户
        CustomerDO customer = this.getCustomerById(customerId);

        if (customer.getState().equals(CustomerStateEnum.NORMAL.getValue())) {
            // 正常状态更改为冻结状态,即冻结账号
            // 判断原状态和新状态是否一致,不一致则更新
            if (customer.getState().compareTo(customerState) != 0) {
                customer.setState(customerState);
                boolean updateResult = this.updateById(customer);
                if (updateResult) {
                    return new UpdatedVO(CodeMessageConstant.FROZEN_CUSTOMER_SUCCESS);
                } else {
                    throw new ParameterException(CodeMessageConstant.FROZEN_CUSTOMER_FAILED);
                }
            } else {
                throw new ForbiddenException(CodeMessageConstant.FORBIDDEN_UPDATE_CUSTOMER_STATE);
            }
        } else {
            // 冻结状态更改为正常状态,即解封账号
            // 判断原状态和新状态是否一致,不一致则更新
            if (customer.getState().compareTo(customerState) != 0) {
                customer.setState(customerState);
                boolean updateResult = this.updateById(customer);
                if (updateResult) {
                    return new UpdatedVO(CodeMessageConstant.UNBAN_CUSTOMER_SUCCESS);
                } else {
                    throw new ParameterException(CodeMessageConstant.UNBAN_CUSTOMER_FAILED);
                }
            } else {
                throw new ForbiddenException(CodeMessageConstant.FORBIDDEN_UPDATE_CUSTOMER_STATE);
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