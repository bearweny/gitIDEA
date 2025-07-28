package com.lsy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lsy.domain.Collect;
import com.lsy.vo.CollectVO;

import java.util.List;

public interface CollectMapper extends BaseMapper<Collect> {
    List<CollectVO> getByUserId(Integer userId,String keyword);
}
