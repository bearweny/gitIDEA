package com.lsy.controller;

import com.lsy.common.R;
import com.lsy.domain.RankList;
import com.lsy.service.IRankListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rankList")
public class RankListController {

    @Autowired // 依赖注入
    private IRankListService rankListService;

    @PostMapping("/add")
    public R add(@RequestBody RankList rankList) {
        rankListService.save(rankList);
        return R.success("添加成功");
    }

    @GetMapping("/user/{consumerId}/{songListId}")
    public R user(@PathVariable Integer consumerId, @PathVariable Integer songListId) {
        RankList rankList = rankListService.user(consumerId, songListId);
        if (rankList != null) {
            return R.success("查询成功", rankList.getScore());
        } else {
            return R.success("查询成功");
        }
    }

}
