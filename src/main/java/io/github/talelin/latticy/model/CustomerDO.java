package io.github.talelin.latticy.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 博客用户的数据模型类
 * </p>
 *
 * @author 踏雪彡寻梅
 * @version 1.0
 * @date 2020-08-04 - 12:35
 * @since JDK1.8
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("customer")
public class CustomerDO extends BaseModel implements Serializable {

    private static final long serialVersionUID = 3605284309859731569L;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 用户的昵称
     */
    private String nickname;

    /**
     * 用户状态: 1-正常,0-冻结
     */
    private Integer state;

    /**
     * 用户头像 url
     */
    private String avatar;

}