package io.github.talelin.latticy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import io.github.talelin.latticy.dto.blog.tag.TagDTO;
import io.github.talelin.latticy.model.TagDO;

/**
 * <p>
 * 标签业务操作接口类
 * </p>
 *
 * @author 踏雪彡寻梅
 * @version 1.0
 * @date 2020-08-01 - 17:42
 * @since JDK1.8
 */
public interface TagService extends IService<TagDO> {

    /**
     * 添加一个标签
     *
     * @param tagDTO 标签的数据传输对象
     */
    void createTag(TagDTO tagDTO);

    /**
     * 根据标签的 ID 获取相应标签的信息
     *
     * @param tagId 标签的 ID
     * @return 返回获取的标签的信息
     */
    TagDO getTagById(Integer tagId);

    /**
     * 根据标签的 ID 更新相应标签的信息
     *
     * @param tagId  标签的 ID
     * @param tagDTO 标签的数据传输对象
     */
    void updateTagById(Integer tagId, TagDTO tagDTO);

    /**
     * 根据标签的 ID 删除相应文章分类
     *
     * @param tagId 标签的 ID
     */
    void deleteTagById(Integer tagId);

    /**
     * 根据分页查询参数 page、count 获取当前页的标签列表
     *
     * @param page  当前页数
     * @param count 每页的标签数量
     * @return 返回封装着获取的标签列表的分页对象
     */
    IPage<TagDO> getTagListByPage(Integer page, Integer count);

}