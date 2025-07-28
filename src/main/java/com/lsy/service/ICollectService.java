package com.lsy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lsy.domain.Collect;
import com.lsy.vo.CollectVO;

import java.util.List;

public interface ICollectService extends IService<Collect> {
    List<CollectVO> getByUserId(Integer userId,String keyword);
    boolean deleteBatch(Integer userId, List<Integer> songIds);

    boolean status(Collect collect);

    void delete(Integer userId, Integer songId);
}
