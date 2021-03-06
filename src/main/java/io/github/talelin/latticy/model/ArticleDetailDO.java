package io.github.talelin.latticy.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * <p>
 * 文章详情信息的数据模型类
 * </p>
 *
 * @author 踏雪彡寻梅
 * @version 1.0
 * @date 2020/7/30 - 23:30
 * @since JDK1.8
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class ArticleDetailDO extends ArticleDO {

    private static final long serialVersionUID = 6031685030595483665L;

    /**
     * 文章关联的标签 ID 列表
     */
    private List<Integer> tagIdList;

    /**
     * 文章所属分类名称
     */
    private String categoryName;

    /**
     * 文章内容
     */
    private String content;

    public ArticleDetailDO(ArticleDO article, List<Integer> tagIdList, String content, String categoryName) {
        BeanUtils.copyProperties(article, this);
        this.tagIdList = tagIdList;
        this.content = content;
        this.categoryName = categoryName;
    }

}