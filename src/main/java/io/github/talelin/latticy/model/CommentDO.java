package io.github.talelin.latticy.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 文章评论的数据模型类
 * </p>
 *
 * @author 踏雪彡寻梅
 * @version 1.0
 * @date 2020-08-08 - 21:31
 * @since JDK1.8
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("comment")
public class CommentDO extends BaseModel implements Serializable {

    private static final long serialVersionUID = 6422941220873608365L;

    /**
     * 评论所属文章的 ID
     */
    private Integer articleId;

    /**
     * 评论所属用户的 ID
     */
    private Integer customerId;

    /**
     * 评论所属管理员用户的 ID
     */
    private Integer adminUserId;

    /**
     * 根评论 ID,即一级评论 ID
     */
    private Integer rootId;

    /**
     * 父级评论 ID
     */
    private Integer parentId;

    /**
     * 标记当前评论是否为一级评论: 1-一级评论,0-二级评论
     * 回复文章的是一级评论,其它的都是二级评论
     */
    private Integer root;

    /**
     * 评论内容/回复内容
     */
    private String content;

    /**
     * 评论者当前 IP
     */
    private String ip;

    /**
     * 评论者当前 IP 解析出的详细地址
     */
    private String address;

    /**
     * 是否显示当前评论: 0-隐藏, 1-显示
     */
    private Integer displayState;

}