package com.lsy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lsy.common.R;
import com.lsy.domain.SongList;
import com.lsy.query.BaseQuery;
import com.lsy.query.SongListQuery;
import com.lsy.service.ISongListService;
import com.lsy.vo.SongVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/songList")
public class SongListController {

    @Autowired // 注入
    private ISongListService songListService;

    @GetMapping("/list")
    public R list() {
        // 调用 service 接口
        List<SongList> list = songListService.list();
        // 响应数据
        return R.success("查询成功", list);
    }
    @PostMapping("/page")
    public R page(@RequestBody BaseQuery query) {
        // 调用 service 接口
        IPage<SongList> page = songListService.songListPage(query);
        // 响应数据
        return R.success("查询成功", page);
    }

    //    删除歌单
    @DeleteMapping("/delete")
    public R delete(@RequestParam Integer id) {
        boolean result = songListService.removeById(id);
        return result ? R.success("删除成功") : R.error("删除失败");
    }
//    批量删除歌单
@PostMapping("/batchDelete")
public R batchDelete(@RequestBody List<Integer> ids) {
    boolean result = songListService.removeBatchByIds(ids);
    return result ? R.success("批量删除成功") : R.error("批量删除失败");
}

//编辑歌单
@PostMapping("/update")
public R update(@RequestBody SongList songList) {
    boolean success = songListService.updateById(songList);
    return success ? R.success("修改成功") : R.error("修改失败");
}

    @PostMapping("/style")
    public R style(@RequestBody SongListQuery query) {
        // 调用 service 接口
        IPage<SongList> page = songListService.style(query);
        // 响应数据
        return R.success("查询成功", page);
    }
    @GetMapping("/search/{keyword}")
    public R search(@PathVariable String keyword){
        List<SongList> list = songListService.search(keyword);
        return R.success("查询成功",list);
    }




}
