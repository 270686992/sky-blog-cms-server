package io.github.talelin.latticy.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 留言的数据模型类
 * </p>
 *
 * @author 踏雪彡寻梅
 * @version 1.0
 * @date 2020-08-09 - 17:26
 * @since JDK1.8
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("leave_message")
public class LeaveMessageDO extends BaseModel implements Serializable {

    private static final long serialVersionUID = 9152438691486487965L;

    /**
     * 留言所属用户的 ID
     */
    private Integer customerId;

    /**
     * 留言所属管理员用户的 ID
     */
    private Integer adminUserId;

    /**
     * 父级留言 ID
     */
    private Integer parentId;

    /**
     * 父级留言所属用户的 ID
     */
    private Integer parentCustomerId;

    /**
     * 被回复的留言的 ID
     */
    private Integer replyId;

    /**
     * 被回复的留言所属用户的 ID
     */
    private Integer replyCustomerId;

    /**
     * 标记当前留言是否为一级留言: 1-一级留言,0-二级留言
     */
    private Integer root;

    /**
     * 留言内容/回复内容
     */
    private String content;

    /**
     * 留言者当前 IP
     */
    private String ip;

    /**
     * 留言者当前 IP 解析出的详细地址
     */
    private String address;

    /**
     * 是否显示当前留言: 0-隐藏,1-显示
     */
    private Integer displayState;

}