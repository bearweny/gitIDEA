package com.lsy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lsy.domain.UserSupport;

public interface IUserSupportService extends IService<UserSupport> {
   Integer test(UserSupport userSupport);
}
