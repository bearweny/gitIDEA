package com.lsy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsy.common.Constants;
import com.lsy.domain.Song;
import com.lsy.mapper.SongMapper;
import com.lsy.query.SongQuery;
import com.lsy.service.ISongService;

import com.lsy.vo.SongVO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.IOUtils;


@Service
public class SongServiceImpl extends ServiceImpl<SongMapper, Song> implements ISongService {
    @Override
    public IPage<Song> songPage(SongQuery query) {
        Page<Song> page = new Page<>(query.getCurrentPage(), query.getPageSize());

        // 条件查询
        LambdaQueryWrapper<Song> wrapper = new LambdaQueryWrapper<>();

        // 拼装 歌手id 条件
        wrapper.eq(Song::getSingerId, query.getSingerId());

        String keyword = query.getKeyword();

        // keyword 不为空 和 不为空字符串
        boolean flag = keyword != null && keyword.length() > 0;

        wrapper.like(flag, Song::getName, keyword);

        // 调用分页
        return page(page, wrapper);
    }

    @Override
    public void add(Song song, MultipartFile lrcfile, MultipartFile file) {
        // 1. 保存歌曲文件
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("上传的音乐文件为空");
        }

        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || !originalFilename.contains(".")) {
            throw new RuntimeException("上传的文件名无效，缺少后缀");
        }

        int start = originalFilename.lastIndexOf(".");
        String ex = originalFilename.substring(start);

        // 可选：校验格式
        if (!ex.equalsIgnoreCase(".mp3") && !ex.equalsIgnoreCase(".wav")) {
            throw new RuntimeException("不支持的音频格式，仅支持 .mp3 / .wav");
        }

        String songName = song.getName() + ex;
        String filename = Constants.SONG_PATH_FILE + songName;

        try (InputStream in = file.getInputStream();
             FileOutputStream fos = new FileOutputStream(filename)) {
            IOUtils.copy(in, fos);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("歌曲文件保存失败：" + e.getMessage());
        }

        // 2. 歌词文件读取（可选）
        if (lrcfile != null && !lrcfile.isEmpty()) {
            try (InputStream in = lrcfile.getInputStream()) {
                String lrcStr = IOUtils.toString(in, "UTF-8");
                song.setLyric(lrcStr);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("歌词文件读取失败：" + e.getMessage());
            }
        }

        // 3. 存储 Song 对象
        song.setCreateTime(new Date());
        song.setUpdateTime(new Date());

        String url = Constants.SONG_PATH + songName;
        song.setUrl(url);

        save(song);
    }

    @Override
    public List<SongVO>listsong(Integer id){
        return baseMapper.listsong(id);
    }

    @Override
    public List<SongVO> search(String keyword) {
        return baseMapper.search(keyword);
    }


}


