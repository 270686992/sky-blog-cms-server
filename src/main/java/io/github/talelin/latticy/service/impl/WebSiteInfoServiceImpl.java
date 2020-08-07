package io.github.talelin.latticy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.talelin.autoconfigure.exception.ForbiddenException;
import io.github.talelin.autoconfigure.exception.NotFoundException;
import io.github.talelin.autoconfigure.exception.ParameterException;
import io.github.talelin.latticy.common.constant.CodeMessageConstant;
import io.github.talelin.latticy.dto.blog.websiteinfo.WebSiteInfoDTO;
import io.github.talelin.latticy.mapper.WebSiteInfoMapper;
import io.github.talelin.latticy.model.WebSiteInfoDO;
import io.github.talelin.latticy.service.WebSiteInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 站点信息业务操作接口类的实现类
 * </p>
 *
 * @author 踏雪彡寻梅
 * @version 1.0
 * @date 2020-08-07 - 22:37
 * @since JDK1.8
 */
@Service
public class WebSiteInfoServiceImpl extends ServiceImpl<WebSiteInfoMapper, WebSiteInfoDO> implements WebSiteInfoService {

    /**
     * 站点信息的最大数量
     */
    @Value("${sky-blog.web-site-info-maximum-quantity}")
    private int maximumQuality;

    @Override
    public void createWebSiteInfo(WebSiteInfoDTO webSiteInfoDTO) {
        // 判断站点信息数量是否已经超过限制
        int webSiteInfoCount = this.count();
        if (webSiteInfoCount >= maximumQuality) {
            throw new ForbiddenException(CodeMessageConstant.WEB_SITE_INFO_EXCEED_MAXIMUM_QUANTITY);
        }

        // 添加站点信息
        WebSiteInfoDO webSiteInfo = new WebSiteInfoDO();
        BeanUtils.copyProperties(webSiteInfoDTO, webSiteInfo);
        boolean saveResult = this.save(webSiteInfo);
        if (!saveResult) {
            throw new ParameterException(CodeMessageConstant.CREATE_WEB_SITE_INFO_FAILED);
        }
    }

    @Override
    public WebSiteInfoDO getWebSiteInfoById(Integer webSiteInfoId) {
        WebSiteInfoDO webSiteInfo = this.getById(webSiteInfoId);

        if (webSiteInfo == null) {
            throw new NotFoundException(CodeMessageConstant.NOT_FOUND_WEB_SITE_INFO);
        }

        return webSiteInfo;
    }

    @Override
    public void updateWebSiteInfoById(Integer webSiteInfoId, WebSiteInfoDTO webSiteInfoDTO) {
        // 获取相应站点信息
        WebSiteInfoDO webSiteInfo = this.getWebSiteInfoById(webSiteInfoId);

        // 更新站点信息
        BeanUtils.copyProperties(webSiteInfoDTO, webSiteInfo);
        boolean updateResult = this.updateById(webSiteInfo);
        if (!updateResult) {
            throw new ParameterException(CodeMessageConstant.UPDATE_WEB_SITE_INFO_FAILED);
        }
    }

    @Override
    public void deleteWebSiteInfoById(Integer webSiteInfoId) {
        // 判断站点信息是否存在
        WebSiteInfoDO webSiteInfo = this.getById(webSiteInfoId);
        if (webSiteInfo == null) {
            throw new NotFoundException(CodeMessageConstant.NOT_FOUND_WEB_SITE_INFO);
        }

        // 删除站点信息
        int deleteResult = this.getBaseMapper().deleteById(webSiteInfoId);
        if (deleteResult <= 0) {
            throw new ParameterException(CodeMessageConstant.DELETE_WEB_SITE_INFO_FAILED);
        }
    }

}