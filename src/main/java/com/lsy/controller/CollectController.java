package com.lsy.controller;

import com.lsy.common.R;
import com.lsy.domain.Collect;
import com.lsy.query.CollectQuery;
import com.lsy.service.ICollectService;
import com.lsy.vo.CollectVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/collect")
public class CollectController {

    @Autowired
    private ICollectService collectService;

    @PostMapping("/list")
    public R getByUserId(@RequestBody CollectQuery query) {
        List<CollectVO> list = collectService.getByUserId(query.getUserId(), query.getKeyword());
        return R.success("查询成功", list);
    }
    //    删除单个收藏歌曲
    @DeleteMapping("/delete/{id}")
    public R delete(@PathVariable Integer id) {
        collectService.removeById(id);
        return R.success("删除成功");
    }

    //    批量删除收藏歌曲
    // Controller 示例
    @PostMapping("/deleteBatch")
    public R deleteBatch(@RequestBody CollectQuery query) {
        boolean flag = collectService.deleteBatch(query.getUserId(), query.getSongIds());
        System.out.println("userId = " + query.getUserId());
        System.out.println("songIds = " + query.getSongIds());

        return flag ? R.success("批量删除成功") : R.error("批量删除失败");
    }

    @PostMapping("/status")
    public R status(@RequestBody Collect collect) {
        boolean flag = collectService.status(collect);
        return R.success("查询成功", flag);
    }
    @PostMapping("/add")
    public R add(@RequestBody Collect collect) {
        collect.setCreateTime(new Date());
        collectService.save(collect);
        return R.success("收藏成功",true);
    }
    @DeleteMapping("/delete/{userId}/{songId}")
    public R delete(@PathVariable Integer userId, @PathVariable Integer songId) {
        collectService.delete(userId, songId);
        return R.success("取消收藏",false);
    }


}



