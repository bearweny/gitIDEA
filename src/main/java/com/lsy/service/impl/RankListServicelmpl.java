package com.lsy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsy.domain.RankList;
import com.lsy.domain.Singer;
import com.lsy.mapper.RankListMapper;
import com.lsy.mapper.SingerMapper;
import com.lsy.service.IRankListService;
import com.lsy.service.ISingerService;
import org.springframework.stereotype.Service;

@Service
public class RankListServicelmpl extends ServiceImpl<RankListMapper, RankList>
        implements IRankListService {


    @Override
    public RankList user(Integer consumerId,Integer songListId){
        LambdaQueryWrapper<RankList> wrapper =new LambdaQueryWrapper<>();
        wrapper.eq(RankList::getConsumerId,consumerId);
        wrapper.eq(RankList::getSongListId,songListId);
        return getOne(wrapper);
    }

}
