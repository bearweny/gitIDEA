package com.lsy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lsy.domain.Admin;

public interface IAdminService extends IService<Admin> {

    Admin login(Admin admin);
}
