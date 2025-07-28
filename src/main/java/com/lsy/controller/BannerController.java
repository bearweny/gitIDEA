package com.lsy.controller;

import com.lsy.common.R;
import com.lsy.domain.Banner;
import com.lsy.service.IBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/banner")
public class BannerController {

    @Autowired
    private IBannerService bannerService;

    @GetMapping("/list")
    public R list() {
        // 调用service接口
        List<Banner> list = bannerService.list();
        // 响应数据
        return R.success("查询成功", list);
    }
}
