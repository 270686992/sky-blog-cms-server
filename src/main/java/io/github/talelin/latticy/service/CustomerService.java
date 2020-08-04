package io.github.talelin.latticy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import io.github.talelin.latticy.model.CustomerDO;

/**
 * <p>
 * 博客用户业务操作接口类
 * </p>
 *
 * @author 踏雪彡寻梅
 * @version 1.0
 * @date 2020-08-04 - 12:35
 * @since JDK1.8
 */
public interface CustomerService extends IService<CustomerDO> {

    /**
     * 根据博客用户的 ID 获取相应用户的信息
     *
     * @param customerId 博客用户的 ID
     * @return 返回获取的博客用户的信息
     */
    CustomerDO getCustomerById(Integer customerId);

    /**
     * 根据博客用户的 ID 修改用户状态为指定状态
     *
     * @param customerId    博客用户的 ID
     * @param customerState 博客用户的新状态
     */
    void updateCustomerStateById(Integer customerId, Integer customerState);

    /**
     * 根据分页查询参数 page、count 获取当前页的博客用户列表
     *
     * @param page  当前页数
     * @param count 每页的博客用户数量
     * @return 返回封装着获取的博客用户列表的分页对象
     */
    IPage<CustomerDO> getCustomerListByPage(Integer page, Integer count);

    /**
     * 根据分页查询参数 page、count,查询关键字 keyword 模糊获取当前页的博客用户列表
     *
     * @param page    当前页数
     * @param count   每页的博客用户数量
     * @param keyword 查询关键字
     * @return 返回封装着模糊获取的博客用户列表的分页对象
     */
    IPage<CustomerDO> searchCustomerList(Integer page, Integer count, String keyword);

}