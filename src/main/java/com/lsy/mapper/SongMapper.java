package com.lsy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lsy.domain.Song;
import com.lsy.vo.SongVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SongMapper extends BaseMapper<Song> {
    List<SongVO> listsong(Integer songListId);

    List<SongVO> search(String keyword);
}
