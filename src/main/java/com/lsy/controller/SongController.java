package com.lsy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lsy.common.R;
import com.lsy.domain.Song;
import com.lsy.query.SongQuery;
import com.lsy.service.ISongService;
import com.lsy.vo.SongVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/song")
public class SongController {

    @Autowired
    private ISongService songService;

    @GetMapping
    public R list() {
        List<Song> list = songService.list();
        return R.success("查询成功", list);
    }

    @PostMapping("/page")
    public R page(@RequestBody SongQuery query) {
        IPage<Song> page = songService.songPage(query);
        return R.success("查询成功", page);
    }
    @PostMapping("/add")
    public R add( @RequestParam("lrcfile") MultipartFile lrcfile,@RequestParam("file") MultipartFile file,Song song) {
        System.out.println(song);
        System.out.println(lrcfile.getOriginalFilename());
        songService.add(song,lrcfile,file);
        return R.success("添加成功");
    }

    @GetMapping("/listsong/{id}")
    public R listsong(@PathVariable Integer id) {
        List<SongVO> list = songService.listsong(id);
        return R.success("查询成功", list);
    }

    @GetMapping("/search/{keyword}")
    public R search(@PathVariable String keyword){
        List<SongVO> list = songService.search(keyword);
        return R.success("查询成功",list);
    }

}
