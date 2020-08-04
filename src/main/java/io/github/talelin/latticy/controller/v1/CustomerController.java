package io.github.talelin.latticy.controller.v1;


import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.talelin.core.annotation.*;
import io.github.talelin.latticy.common.constant.CodeMessageConstant;
import io.github.talelin.latticy.common.util.PageUtil;
import io.github.talelin.latticy.model.CustomerDO;
import io.github.talelin.latticy.service.CustomerService;
import io.github.talelin.latticy.vo.PageResponseVO;
import io.github.talelin.latticy.vo.UpdatedVO;
import org.hibernate.validator.constraints.Range;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * <p>
 * 博客用户业务接口控制器
 * </p>
 *
 * @author 踏雪彡寻梅
 * @version 1.0
 * @date 2020-08-04 - 12:35
 * @since JDK1.8
 */
@RestController
@Validated
@RequestMapping("/v1/customer")
@PermissionModule(value = "博客用户")
public class CustomerController extends BaseController {

    /**
     * 博客用户业务操作对象
     */
    private final CustomerService customerService;

    /**
     * 构造函数,注入该类需要的对象
     *
     * @param customerService 博客用户业务操作对象
     */
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * 根据博客用户的 ID 获取相应用户的信息
     *
     * @param customerId 博客用户的 ID
     * @return 返回获取的博客用户的信息
     */
    @GetMapping("/{id}")
    @LoginRequired
    public CustomerDO getCustomerById(@PathVariable(name = "id") @Positive(message = "{id.positive}") Integer customerId) {
        return this.customerService.getCustomerById(customerId);
    }

    /**
     * 根据博客用户的 ID 修改用户状态为指定状态
     *
     * @param customerId    博客用户的 ID
     * @param customerState 博客用户的新状态
     * @return 更新成功返回成功的响应结果, 否则返回失败的响应结果
     */
    @PutMapping("/state")
    @PermissionMeta(value = UPDATE + "博客用户状态")
    @GroupRequired
    @Logger(template = "{user.nickname} 更新了一个博客用户的状态")
    public UpdatedVO updateCustomerStateById(@RequestParam(name = "id") @NotNull(message = "{id.not-null}") @Positive(message = "{id.positive}") Integer customerId,
                                             @RequestParam(name = "state") @NotNull(message = "{customer.state.not-null}") @Range(min = 0, max = 1, message = "{customer.state.range}") Integer customerState) {
        this.customerService.updateCustomerStateById(customerId, customerState);
        return new UpdatedVO(CodeMessageConstant.UPDATE_CUSTOMER_STATE_SUCCESS);
    }

    /**
     * 根据分页查询参数 page、count 获取当前页的博客用户列表
     *
     * @param page  当前页数
     * @param count 每页的博客用户数量
     * @return 返回封装着获取的博客用户列表的分页视图对象
     */
    @GetMapping("/page")
    @LoginRequired
    public PageResponseVO<CustomerDO> getCustomerListByPage(@RequestParam(name = "page", required = false, defaultValue = "0")
                                                            @Min(value = 0, message = "{page.number.min}") Integer page,
                                                            @RequestParam(name = "count", required = false, defaultValue = "10")
                                                            @Min(value = 1, message = "{page.count.min}")
                                                            @Max(value = 30, message = "{page.count.max}") Integer count) {
        // 获取封装着查询结果信息的分页对象
        IPage<CustomerDO> customerPage = this.customerService.getCustomerListByPage(page, count);
        // 使用分页对象构建分页视图对象并返回
        return PageUtil.build(customerPage);
    }

    /**
     * 根据分页查询参数 page、count,查询关键字 keyword 模糊获取当前页的博客用户列表
     *
     * @param page    当前页数
     * @param count   每页的博客用户数量
     * @param keyword 查询关键字
     * @return 返回封装着模糊获取的博客用户列表的分页视图对象
     */
    @GetMapping("/search")
    @LoginRequired
    public PageResponseVO<CustomerDO> searchCustomerList(@RequestParam(name = "page", required = false, defaultValue = "0")
                                                         @Min(value = 0, message = "{page.number.min}") Integer page,
                                                         @RequestParam(name = "count", required = false, defaultValue = "10")
                                                         @Min(value = 1, message = "{page.count.min}")
                                                         @Max(value = 30, message = "{page.count.max}") Integer count,
                                                         @RequestParam(name = "keyword", required = false, defaultValue = "") String keyword) {
        // 获取封装着查询结果信息的分页对象
        IPage<CustomerDO> customerPage = this.customerService.searchCustomerList(page, count, keyword);
        // 使用分页对象构建分页视图对象并返回
        return PageUtil.build(customerPage);
    }

}