package io.github.talelin.latticy.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;


/**
 * <p>
 * 标签的数据模型类
 * </p>
 *
 * @author 踏雪彡寻梅
 * @version 1.0
 * @date 2020-08-01 - 17:42
 * @since JDK1.8
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("tag")
public class TagDO extends BaseModel implements Serializable {

    private static final long serialVersionUID = 6758394446201672837L;

    /**
     * 标签名称
     */
    private String name;

    /**
     * 标签的上线状态: 1-上线,0-下线
     */
    private Integer online;

}