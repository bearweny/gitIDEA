package com.lsy.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lsy.domain.Singer;
import com.lsy.query.BaseQuery;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Collection;

public interface ISingerService extends IService<Singer> {
    IPage<Singer> singerPage(BaseQuery query);


    void avatarUpdate(Integer id, MultipartFile file);
}
