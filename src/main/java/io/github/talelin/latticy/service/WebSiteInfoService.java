package io.github.talelin.latticy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.talelin.latticy.dto.blog.websiteinfo.WebSiteInfoDTO;
import io.github.talelin.latticy.model.WebSiteInfoDO;

/**
 * <p>
 * 站点信息业务操作接口类
 * </p>
 *
 * @author 踏雪彡寻梅
 * @version 1.0
 * @date 2020-08-07 - 22:37
 * @since JDK1.8
 */
public interface WebSiteInfoService extends IService<WebSiteInfoDO> {

    /**
     * 添加一个站点信息
     *
     * @param webSiteInfoDTO 站点信息的数据传输对象
     */
    void createWebSiteInfo(WebSiteInfoDTO webSiteInfoDTO);

    /**
     * 根据站点信息的 ID 获取相应站点信息的信息
     *
     * @param webSiteInfoId 站点信息的 ID
     * @return 返回获取的站点信息的信息
     */
    WebSiteInfoDO getWebSiteInfoById(Integer webSiteInfoId);

    /**
     * 根据站点信息的 ID 更新相应站点信息的信息
     *
     * @param webSiteInfoId  站点信息的 ID
     * @param webSiteInfoDTO 站点信息的数据传输对象
     */
    void updateWebSiteInfoById(Integer webSiteInfoId, WebSiteInfoDTO webSiteInfoDTO);

    /**
     * 根据站点信息的 ID 删除相应友情链接
     *
     * @param webSiteInfoId 站点信息的 ID
     */
    void deleteWebSiteInfoById(Integer webSiteInfoId);

}