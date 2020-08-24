package io.github.talelin.latticy.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 站点信息的数据模型类
 * </p>
 *
 * @author 踏雪彡寻梅
 * @version 1.0
 * @date 2020-08-07 - 22:37
 * @since JDK1.8
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("web_site_info")
public class WebSiteInfoDO extends BaseModel implements Serializable {

    private static final long serialVersionUID = -3725259282387509141L;

    /**
     * 站点名称
     */
    private String name;

    /**
     * 站点个性标题
     */
    private String title;

    /**
     * 站点个性签名
     */
    private String signature;

    /**
     * 版权时间,例如: 2020-2020
     */
    private String copyrightTime;

    /**
     * 站点版本号,例如: V1.0.0
     */
    private String versionNumber;

    /**
     * ICP 备案号
     */
    private String icpNumber;

    /**
     * 站点作者昵称,用于展示在前台
     */
    private String nickname;

    /**
     * 站点作者头像 url
     */
    private String authorAvatar;

    /**
     * 站点图标 url
     */
    private String icon;

    /**
     * 评论者/留言者默认头像 url,评论/留言用户没有设置头像时进行评论/留言时使用该默认头像
     */
    private String commentatorAvatar;

    /**
     * 友链申请说明
     */
    private String applicationExplain;

    /**
     * 关于我的说明
     */
    private String aboutMeExplain;

    /**
     * 关于站点的说明
     */
    private String aboutSiteExplain;

    /**
     * 关于版权的说明
     */
    private String aboutCopyrightExplain;

    /**
     * 特别说明
     */
    private String specialExplain;

    /**
     * 站点公告
     */
    private String announcement;

}