package io.github.talelin.latticy.controller.v1;


import io.github.talelin.core.annotation.*;
import io.github.talelin.latticy.common.constant.CodeMessageConstant;
import io.github.talelin.latticy.dto.blog.websiteinfo.WebSiteInfoDTO;
import io.github.talelin.latticy.model.WebSiteInfoDO;
import io.github.talelin.latticy.service.WebSiteInfoService;
import io.github.talelin.latticy.vo.CreatedVO;
import io.github.talelin.latticy.vo.DeletedVO;
import io.github.talelin.latticy.vo.UpdatedVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.util.List;

/**
 * <p>
 * 站点信息业务接口控制器
 * </p>
 *
 * @author 踏雪彡寻梅
 * @version 1.0
 * @date 2020-08-07 - 22:37
 * @since JDK1.8
 */
@RestController
@Validated
@RequestMapping("/v1/web-site-info")
@PermissionModule(value = "站点信息")
public class WebSiteInfoController extends BaseController {

    /**
     * 模块名称
     */
    private static final String MODULE_NAME = "站点信息";

    /**
     * 站点信息业务操作对象
     */
    private final WebSiteInfoService webSiteInfoService;

    /**
     * 构造函数,注入该类需要的对象
     *
     * @param webSiteInfoService 站点信息业务操作对象
     */
    public WebSiteInfoController(WebSiteInfoService webSiteInfoService) {
        this.webSiteInfoService = webSiteInfoService;
    }

    /**
     * 添加一个站点信息
     *
     * @param webSiteInfoDTO 站点信息的数据传输对象
     * @return 添加成功返回成功的响应结果, 否则返回失败的响应结果
     */
    @PostMapping("")
    @PermissionMeta(value = CREATE + MODULE_NAME)
    @GroupRequired
    @Logger(template = "{user.nickname} 添加了一个站点信息")
    public CreatedVO createWebSiteInfo(@RequestBody @Validated WebSiteInfoDTO webSiteInfoDTO) {
        this.webSiteInfoService.createWebSiteInfo(webSiteInfoDTO);
        return new CreatedVO(CodeMessageConstant.CREATE_WEB_SITE_INFO_SUCCESS);
    }

    /**
     * 根据站点信息的 ID 获取相应站点信息的信息
     *
     * @param webSiteInfoId 站点信息的 ID
     * @return 返回获取的站点信息的信息
     */
    @GetMapping("/{id}")
    @LoginRequired
    public WebSiteInfoDO getWebSiteInfoById(@PathVariable(name = "id") @Positive(message = "{id.positive}") Integer webSiteInfoId) {
        return this.webSiteInfoService.getWebSiteInfoById(webSiteInfoId);
    }

    /**
     * 根据站点信息的 ID 更新相应站点信息的信息
     *
     * @param webSiteInfoId  站点信息的 ID
     * @param webSiteInfoDTO 站点信息的数据传输对象
     * @return 更新成功返回成功的响应结果, 否则返回失败的响应结果
     */
    @PutMapping("/{id}")
    @PermissionMeta(value = UPDATE + MODULE_NAME)
    @GroupRequired
    @Logger(template = "{user.nickname} 更新了一个站点信息")
    public UpdatedVO updateWebSiteInfoById(@PathVariable(name = "id") @Positive(message = "{id.positive}") Integer webSiteInfoId,
                                           @RequestBody @Validated WebSiteInfoDTO webSiteInfoDTO) {
        this.webSiteInfoService.updateWebSiteInfoById(webSiteInfoId, webSiteInfoDTO);
        return new UpdatedVO(CodeMessageConstant.UPDATE_WEB_SITE_INFO_SUCCESS);
    }

    /**
     * 根据站点信息的 ID 删除相应友情链接
     *
     * @param webSiteInfoId 站点信息的 ID
     * @return 删除成功返回成功的响应结果, 否则返回失败的响应结果
     */
    @DeleteMapping("/{id}")
    @PermissionMeta(value = DELETE + MODULE_NAME)
    @GroupRequired
    @Logger(template = "{user.nickname} 删除了一个站点信息")
    public DeletedVO deleteWebSiteInfoById(@PathVariable(name = "id") @Positive(message = "{id.positive}") Integer webSiteInfoId) {
        this.webSiteInfoService.deleteWebSiteInfoById(webSiteInfoId);
        return new DeletedVO(CodeMessageConstant.DELETE_WEB_SITE_INFO_SUCCESS);
    }

    /**
     * 获取所有站点信息
     *
     * @return 返回所有站点信息
     */
    @GetMapping("/list")
    @LoginRequired
    public List<WebSiteInfoDO> getAllWebSiteInfoList() {
        return this.webSiteInfoService.list();
    }

}