package com.lsy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsy.domain.Consumer;
import com.lsy.domain.SongList;
import com.lsy.mapper.ConsumerMapper;
import com.lsy.mapper.SongListMapper;
import com.lsy.query.BaseQuery;
import com.lsy.query.SongListQuery;
import com.lsy.service.IConsumerService;
import com.lsy.service.ISongListService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongListServiceImpl extends ServiceImpl<SongListMapper, SongList> implements ISongListService {
    @Override
    public IPage<SongList> songListPage(BaseQuery query) {
        IPage<SongList> page = new Page<>(query.getCurrentPage(), query.getPageSize());
        String keyword = query.getKeyword();
        boolean flag = keyword != null && keyword.length() > 0;

        LambdaQueryWrapper<SongList> wrapper = new LambdaQueryWrapper<>();

        // title introduction 模糊查询
        wrapper.like(flag, SongList::getTitle, keyword)
                .or(flag)
                .like(flag, SongList::getIntroduction, keyword);

        return page(page, wrapper);
    }

    @Override
    public IPage<SongList> style(SongListQuery query) {
        IPage<SongList> page = new Page<>(query.getCurrentPage(), query.getPageSize());
        String style = query.getStyle();
        boolean flag = style != null && style.length() > 0;

        LambdaQueryWrapper<SongList> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(flag, SongList::getStyle, style);

        return page(page, wrapper);
    }
    @Override
    public List<SongList> search(String keyword) {
        LambdaQueryWrapper<SongList> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(SongList::getTitle, keyword).or().like(SongList::getIntroduction, keyword);
        return list(wrapper);
    }



}

