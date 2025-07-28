package com.lsy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsy.common.Constants;
import com.lsy.domain.Singer;
import com.lsy.domain.Song;
import com.lsy.mapper.SingerMapper;
import com.lsy.query.BaseQuery;
import com.lsy.service.ISingerService;
import com.lsy.service.ISongService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.UUID;

@Service // 标注为服务类
public class SingerServiceImpl extends ServiceImpl<SingerMapper, Singer> implements ISingerService {

    @Autowired
    private ISongService songService; // 注入歌曲服务

    @Override
    public IPage<Singer> singerPage(BaseQuery query) {
        // 1. 构建分页对象
        IPage<Singer> page = new Page<>(query.getCurrentPage(), query.getPageSize());

        // 2. 构建查询条件
        LambdaQueryWrapper<Singer> wrapper = new LambdaQueryWrapper<>();
        String keyword = query.getKeyword();
        boolean flag = keyword != null && keyword.length() > 0;
        wrapper.like(flag, Singer::getName, keyword);

        // 3. 返回分页数据
        return page(page, wrapper);
    }

    // ✅ 单个删除：级联删除该歌手的所有歌曲
    @Override
    public boolean removeById(Serializable id) {
        // 删除该歌手的所有歌曲
        songService.remove(new LambdaQueryWrapper<Song>().eq(Song::getSingerId, id));
        // 删除歌手本身
        return super.removeById(id);
    }

    // ✅ 批量删除：级联删除多个歌手的所有歌曲
    @Override
    public boolean removeByIds(Collection<? > idList) {
        // 删除多个歌手的所有歌曲
        songService.remove(new LambdaQueryWrapper<Song>().in(Song::getSingerId, idList));
        // 删除多个歌手
        return super.removeByIds(idList);
    }

    @Override
    public void avatarUpdate(Integer id, MultipartFile file) {
        // 1. 删除旧文件
        Singer singer = getById(id);
        if (singer == null) {
            throw new RuntimeException("未找到该歌手，ID=" + id);
        }

        try {
            // 获取数据库原来存的头像路径
            String pic = singer.getPic();
            if (pic != null && !pic.isEmpty()) {
                String filePath = Constants.ASSETS_PATH + pic;
                // 获取文件的路径
                Path path = Paths.get(filePath);
                if (Files.exists(path)) {
                    Files.delete(path);
                    System.out.println("✅ 成功删除旧头像：" + filePath);
                } else {
                    System.out.println("⚠️ 旧头像文件不存在：" + filePath + "，跳过删除");
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("旧文件删除失败");
        }

        // 2. 文件上传 java内存 --> 硬盘 copy 操作 IO流
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) {
            throw new RuntimeException("上传文件名为空");
        }

        // 拼接：头像存储路径
        String uuid = UUID.randomUUID().toString();
        String filename = Constants.SINGER_IMAGES_FILE + uuid + originalFilename;

        // 文件 copy
        try (InputStream in = file.getInputStream();
             FileOutputStream fos = new FileOutputStream(filename)) {
            IOUtils.copy(in, fos);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("新文件上传失败");
        }

        // 更新数据库中的图片字段
        singer.setPic(Constants.SINGER_IMAGES + uuid + originalFilename);
        updateById(singer);
    }


}


