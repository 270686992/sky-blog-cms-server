package io.github.talelin.latticy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.talelin.autoconfigure.exception.NotFoundException;
import io.github.talelin.autoconfigure.exception.ParameterException;
import io.github.talelin.latticy.common.constant.CodeMessageConstant;
import io.github.talelin.latticy.common.mybatis.Page;
import io.github.talelin.latticy.dto.blog.friendlink.FriendLinkDTO;
import io.github.talelin.latticy.mapper.FriendLinkMapper;
import io.github.talelin.latticy.model.FriendLinkDO;
import io.github.talelin.latticy.service.FriendLinkService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 友情链接业务操作接口类的实现类
 * </p>
 *
 * @author 踏雪彡寻梅
 * @version 1.0
 * @date 2020-08-03 - 22:13
 * @since JDK1.8
 */
@Service
public class FriendLinkServiceImpl extends ServiceImpl<FriendLinkMapper, FriendLinkDO> implements FriendLinkService {

    @Override
    public void createFriendLink(FriendLinkDTO friendLinkDTO) {
        FriendLinkDO friendLink = new FriendLinkDO();

        BeanUtils.copyProperties(friendLinkDTO, friendLink);

        boolean saveResult = this.save(friendLink);
        if (!saveResult) {
            throw new ParameterException(CodeMessageConstant.CREATE_FRIEND_LINK_FAILED);
        }
    }

    @Override
    public FriendLinkDO getFriendLinkById(Integer friendLinkId) {
        FriendLinkDO friendLink = this.getById(friendLinkId);

        if (friendLink == null) {
            throw new NotFoundException(CodeMessageConstant.NOT_FOUND_FRIEND_LINK);
        }

        return friendLink;
    }

    @Override
    public void updateFriendLinkById(Integer friendLinkId, FriendLinkDTO friendLinkDTO) {
        FriendLinkDO friendLink = this.getFriendLinkById(friendLinkId);

        BeanUtils.copyProperties(friendLinkDTO, friendLink);
        boolean updateResult = this.updateById(friendLink);
        if (!updateResult) {
            throw new ParameterException(CodeMessageConstant.UPDATE_FRIEND_LINK_FAILED);
        }
    }

    @Override
    public void deleteFriendLinkById(Integer friendLinkId) {
        FriendLinkDO friendLink = this.getById(friendLinkId);
        if (friendLink == null) {
            throw new NotFoundException(CodeMessageConstant.NOT_FOUND_FRIEND_LINK);
        }

        int deleteResult = this.getBaseMapper().deleteById(friendLinkId);
        if (deleteResult <= 0) {
            throw new ParameterException(CodeMessageConstant.DELETE_FRIEND_LINK_FAILED);
        }
    }

    @Override
    public IPage<FriendLinkDO> getFriendLinkListByPage(Integer page, Integer count) {
        // 构建分页查询对象
        Page<FriendLinkDO> pager = new Page<>(page, count);

        // 构建查询对象
        LambdaQueryWrapper<FriendLinkDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.gt(FriendLinkDO::getPriority, 0)
                .orderByAsc(FriendLinkDO::getPriority);

        // 返回封装着查询结果信息的分页对象
        return this.page(pager, wrapper);
    }

}