package com.lsy.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lsy.domain.Song;
import com.lsy.query.SongQuery;
import com.lsy.vo.SongVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ISongService extends IService<Song> {


    IPage<Song> songPage(SongQuery query);
    void add(Song song, MultipartFile lrcfile, MultipartFile file);

    List<SongVO> listsong(Integer id);

    List<SongVO> search(String keyword);
}
