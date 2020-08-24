package io.github.talelin.latticy.dto.blog.websiteinfo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 站点信息的数据传输类
 * </p>
 *
 * @author 踏雪彡寻梅
 * @version 1.0
 * @date 2020/8/7 - 22:54
 * @since JDK1.8
 */
@Data
public class WebSiteInfoDTO {

    /**
     * 站点名称
     */
    @NotBlank(message = "{web-site-info.name.not-blank}")
    @Length(min = 1, max = 50, message = "{web-site-info.name.length}")
    private String name;

    /**
     * 站点个性标题
     */
    @NotBlank(message = "{web-site-info.title.not-blank}")
    @Length(min = 1, max = 255, message = "{web-site-info.title.length}")
    private String title;

    /**
     * 站点个性签名
     */
    @NotBlank(message = "{web-site-info.signature.not-blank}")
    @Length(min = 1, max = 255, message = "{web-site-info.signature.length}")
    private String signature;

    /**
     * 版权时间,例如: 2020-2020
     */
    @NotBlank(message = "{web-site-info.copyright-time.not-blank}")
    @Length(min = 1, max = 25, message = "{web-site-info.copyright-time.length}")
    private String copyrightTime;

    /**
     * 站点版本号,例如: V1.0.0
     */
    @NotBlank(message = "{web-site-info.version-number.not-blank}")
    @Length(min = 1, max = 25, message = "{web-site-info.version-number.length}")
    private String versionNumber;

    /**
     * ICP 备案号
     */
    @NotBlank(message = "{web-site-info.icp-number.not-blank}")
    @Length(min = 1, max = 50, message = "{web-site-info.icp-number.length}")
    private String icpNumber;

    /**
     * 站点作者昵称,用于展示在前台
     */
    @NotBlank(message = "{web-site-info.nickname.not-blank}")
    @Length(min = 1, max = 20, message = "{web-site-info.nickname.length}")
    private String nickname;

    /**
     * 站点作者头像 url
     */
    @NotBlank(message = "{image.not-blank}")
    @Length(min = 1, max = 255, message = "{image.length}")
    private String authorAvatar;

    /**
     * 站点图标 url
     */
    @NotBlank(message = "{image.not-blank}")
    @Length(min = 1, max = 255, message = "{image.length}")
    private String icon;

    /**
     * 评论者/留言者默认头像 url,评论/留言用户没有设置头像时进行评论/留言时使用该默认头像
     */
    @NotBlank(message = "{image.not-blank}")
    @Length(min = 1, max = 255, message = "{image.length}")
    private String commentatorAvatar;

    /**
     * 友链申请说明
     */
    @Length(max = 500, message = "{web-site-info.application-explain.length}")
    private String applicationExplain;

    /**
     * 关于我的说明
     */
    @Length(max = 500, message = "{web-site-info.about-me-explain.length}")
    private String aboutMeExplain;

    /**
     * 关于站点的说明
     */
    @Length(max = 500, message = "{web-site-info.about-site-explain.length}")
    private String aboutSiteExplain;

    /**
     * 关于版权的说明
     */
    @Length(max = 500, message = "{web-site-info.about-copyright-explain.length}")
    private String aboutCopyrightExplain;

    /**
     * 特别说明
     */
    @Length(max = 500, message = "{web-site-info.special-explain.length}")
    private String specialExplain;

    /**
     * 站点公告
     */
    @Length(max = 500, message = "{web-site-info.announcement.length}")
    private String announcement;

}