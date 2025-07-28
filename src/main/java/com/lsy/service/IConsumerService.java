package com.lsy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lsy.domain.Consumer;
import com.lsy.dto.ResetPasswordDTO;
import com.lsy.query.BaseQuery;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IConsumerService extends IService<Consumer> {
    // 如果有其他自定义方法可以在这里补充
    IPage<Consumer> pageConsumer(BaseQuery query);
    void avatarUpdate(Integer id, MultipartFile file);

    void register(Consumer consumer);

    Consumer login(Consumer consumer);

    Consumer loginByEmail(Consumer consumer);

   void sendVerificationCode(String email);

    void resetPassword(ResetPasswordDTO dto);
}
