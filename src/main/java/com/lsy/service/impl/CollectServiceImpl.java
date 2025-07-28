package com.lsy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsy.domain.Collect;
import com.lsy.mapper.CollectMapper;
import com.lsy.service.ICollectService;
import com.lsy.vo.CollectVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectServiceImpl extends ServiceImpl<CollectMapper, Collect>
        implements ICollectService {

    // 获取收藏歌曲列表
    @Override
    public List<CollectVO> getByUserId(Integer userId, String keyword) {
        return baseMapper.getByUserId(userId, keyword);
    }

    // 批量删除收藏歌曲
    @Override
    public boolean deleteBatch(Integer userId, List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return false;
        }

        System.out.println("userId = " + userId);
        System.out.println("collectIds = " + ids);

        // 根据主键 id 删除，不再使用 userId 和 songId
        return this.removeByIds(ids);
    }

    @Override
    public boolean status(Collect collect) {
        LambdaQueryWrapper<Collect> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Collect::getUserId, collect.getUserId());
        wrapper.eq(Collect::getSongId, collect.getSongId());
        return getOne(wrapper) != null;
    }

    @Override
    public void delete(Integer userId, Integer songId) {
        LambdaQueryWrapper<Collect> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Collect::getSongId,songId);
        wrapper.eq(Collect::getUserId,userId);
        remove(wrapper);
    }


}


