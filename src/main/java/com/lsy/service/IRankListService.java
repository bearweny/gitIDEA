package com.lsy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lsy.domain.RankList;

public interface IRankListService extends IService<RankList> {


    RankList user(Integer consumerId, Integer songListId);
}
