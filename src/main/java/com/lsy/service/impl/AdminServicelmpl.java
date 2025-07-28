package com.lsy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsy.domain.Admin;
import com.lsy.mapper.AdminMapper;
import com.lsy.service.IAdminService;
import org.springframework.stereotype.Service;

@Service
public class AdminServicelmpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService{
    @Override
    public Admin login(Admin admin) {
        //登陆逻辑
        //1、先查用户名  mybatisPlus   SELECT * FROM AMDIN WHERE name = ?
        LambdaQueryWrapper<Admin> wrapper = new LambdaQueryWrapper<>();
        // WHERE name = admin.getName()
        wrapper.eq(Admin::getName,admin.getName());
        //执行查询操作
        Admin dbAdmin = getOne(wrapper);
        if(dbAdmin == null) {
            //用户名不存在
            throw new RuntimeException("用户名错误");
        }
        //2、再比较密码   数据库中的密码和前端传递的密码比较
        if(!dbAdmin.getPassword().equals(admin.getPassword())) {
            throw new RuntimeException("密码错误");
        }
        return dbAdmin;
    }

}
