package com.lsy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsy.domain.Banner;
import com.lsy.mapper.BannerMapper;
import com.lsy.service.IBannerService;
import org.springframework.stereotype.Service;

@Service
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner>
        implements IBannerService {

}
