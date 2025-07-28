package com.lsy.controller;

import com.lsy.common.R;
import com.lsy.domain.Admin;
import com.lsy.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private IAdminService adminService;

    @GetMapping("/list")
    public List<Admin> list() {
        //调用service接口
        List<Admin> list = adminService.list();
        //响应数据
        return list;
    }
    @PostMapping("/login")
    public R login(@RequestBody Admin admin) {
        //单独处理异常 ，统一异常处理
        try {
            Admin dbAdmin = adminService.login(admin);
            return R.success("登陆成功", dbAdmin.getName());
        }catch (Exception e) {
            return R.error(e.getMessage());
        }
    }



}

