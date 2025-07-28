package com.lsy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lsy.common.R;
import com.lsy.domain.Singer;
import com.lsy.domain.UserSupport;
import com.lsy.query.BaseQuery;
import com.lsy.service.ISingerService;
import com.lsy.service.IUserSupportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/userSupport")
public class UserSupportController {

    @Autowired // 依赖注入
    private IUserSupportService supportService;

    @PostMapping("/test")
    public R test(@RequestBody UserSupport userSupport) {
        // 调用service接口
        Integer up=supportService.test(userSupport);
        // 响应数据
        return R.success("操作成功");
    }
}