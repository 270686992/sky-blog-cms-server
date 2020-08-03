package io.github.talelin.latticy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import io.github.talelin.latticy.dto.blog.friendlink.FriendLinkDTO;
import io.github.talelin.latticy.model.FriendLinkDO;

/**
 * <p>
 * 友情链接业务操作接口类
 * </p>
 *
 * @author 踏雪彡寻梅
 * @version 1.0
 * @date 2020-08-03 - 22:13
 * @since JDK1.8
 */
public interface FriendLinkService extends IService<FriendLinkDO> {

    /**
     * 添加一个友情链接
     *
     * @param friendLinkDTO 友情链接的数据传输对象
     */
    void createFriendLink(FriendLinkDTO friendLinkDTO);

    /**
     * 根据友情链接的 ID 获取相应友情链接的信息
     *
     * @param friendLinkId 友情链接的 ID
     * @return 返回获取的友情链接的信息
     */
    FriendLinkDO getFriendLinkById(Integer friendLinkId);

    /**
     * 根据友情链接的 ID 更新相应友情链接的信息
     *
     * @param friendLinkId  友情链接的 ID
     * @param friendLinkDTO 友情链接的数据传输对象
     */
    void updateFriendLinkById(Integer friendLinkId, FriendLinkDTO friendLinkDTO);

    /**
     * 根据友情链接的 ID 删除相应友情链接
     *
     * @param friendLinkId 友情链接的 ID
     */
    void deleteFriendLinkById(Integer friendLinkId);

    /**
     * 根据分页查询参数 page、count 获取当前页的友情链接列表
     *
     * @param page  当前页数
     * @param count 每页的友情链接数量
     * @return 返回封装着获取的友情链接列表的分页对象
     */
    IPage<FriendLinkDO> getFriendLinkListByPage(Integer page, Integer count);

}