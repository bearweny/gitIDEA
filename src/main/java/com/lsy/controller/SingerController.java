package com.lsy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lsy.common.R;
import com.lsy.domain.Singer;
import com.lsy.query.BaseQuery;
import com.lsy.service.ISingerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/singer")
public class SingerController {

    @Autowired
    private ISingerService singerService;

    @GetMapping
    public R list() {
        List<Singer> list = singerService.list();
        return R.success("查询成功", list);
    }

    @PostMapping("/page")
    public R page(@RequestBody BaseQuery query) {
        IPage<Singer> page = singerService.singerPage(query);
        return R.success("查询成功", page);
    }

//    单个删除歌手
@DeleteMapping("/delete")
public R delete(@RequestParam Integer id) {
    boolean result = singerService.removeById(id);
    return result ? R.success("删除成功") : R.error("删除失败");
}

//批零删除歌手
@DeleteMapping("/batchDelete")
public R batchDelete(@RequestBody List<Integer> ids) {
    boolean flag = singerService.removeByIds(ids);
    return flag ? R.success("批量删除成功") : R.error("批量删除失败");
}

//添加歌手
    @PostMapping("/save")
    public  R save(@RequestBody Singer singer){
        singerService.saveOrUpdate(singer);
        return R.success("添加成功");
    }

    @PostMapping("/avatar/{id}")
    public R avatarUpdate(@PathVariable Integer id, MultipartFile file) {
        try {
            singerService.avatarUpdate(id, file);
            return R.success("更新成功");
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
    }
    @GetMapping("/sex/{sex}")
    public R getSingerBySex(@PathVariable Integer sex) {
        List<Singer> list = singerService.lambdaQuery()
                .eq(Singer::getSex, sex)
                .list();
        return R.success("按性别查询成功", list);
    }



}
