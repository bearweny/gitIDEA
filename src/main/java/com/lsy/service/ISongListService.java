package com.lsy.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lsy.domain.Consumer;
import com.lsy.domain.SongList;
import com.lsy.query.BaseQuery;
import com.lsy.query.SongListQuery;

import java.util.List;

public interface ISongListService extends IService<SongList> {


    IPage<SongList> songListPage(BaseQuery query);

    IPage<SongList> style(SongListQuery query);

    List<SongList> search(String keyword);
}
