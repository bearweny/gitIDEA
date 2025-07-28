package com.lsy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsy.common.CacheUtils;
import com.lsy.common.Constants;
import com.lsy.domain.Admin;
import com.lsy.domain.Consumer;
import com.lsy.dto.ResetPasswordDTO;
import com.lsy.mapper.AdminMapper;
import com.lsy.mapper.ConsumerMapper;
import com.lsy.query.BaseQuery;
import com.lsy.service.IAdminService;
import com.lsy.service.IConsumerService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.UUID;

@Service
public class ConsumerServiceImpl extends ServiceImpl<ConsumerMapper, Consumer> implements IConsumerService {

    @Override
    public IPage<Consumer> pageConsumer(BaseQuery query) {
        // 实现分页
        IPage<Consumer> page = new Page<>(query.getCurrentPage(), query.getPageSize());

        // 条件查询
        LambdaQueryWrapper<Consumer> wrapper = new LambdaQueryWrapper<>();
        String keyword = query.getKeyword();
        boolean flag = keyword != null && keyword.length() > 0;
        wrapper.like(flag, Consumer::getUsername, keyword);

        // 返回分页结果
        return this.page(page, wrapper);
    }
    @Override
    public void avatarUpdate(Integer id, MultipartFile file) {
        // 0. 删除文件
        try {
            Consumer dbConsumer = getById(id);
            String filePath = Constants.ASSETS_PATH + dbConsumer.getAvator();
            Path path = Paths.get(filePath);
            System.out.println("旧头像路径：" + filePath);
            System.out.println("文件是否存在：" + Files.exists(path));

            boolean deleted = Files.deleteIfExists(path);
            System.out.println("文件删除结果：" + deleted);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("删除旧头像失败：" + e.getMessage());
        }



        // 1. 获取原始文件名并拼接 UUID，避免重名
        System.out.println("更新头像的用户 ID 是：" + id);

        String originalFilename = file.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();
        String filename = uuid + originalFilename; // ✅ 真正保存的文件名
        String filePath = Constants.AVATOR_IMAGES_FILE + filename; // 本地完整路径

        // 2. 文件上传
        try (InputStream in = file.getInputStream();
             FileOutputStream fos = new FileOutputStream(filePath)) {
            IOUtils.copy(in, fos);
        } catch (Exception e) {
            throw new RuntimeException("头像上传失败", e);
        }

        // 3. 更新数据库中的头像相对路径
        Consumer consumer = new Consumer();
        consumer.setId(id);
        consumer.setAvator(Constants.AVATOR_IMAGES + filename); // ✅ 保存的是带 uuid 的文件名
        updateById(consumer);
    }

    @Override
    public void register(Consumer consumer) {
        // 注册
        String username = consumer.getUsername();
        String email = consumer.getEmail();
        String phone = consumer.getPhoneNum();

        // 1. 判断用户名是否存在
        LambdaQueryWrapper<Consumer> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Consumer::getUsername, username);
        Consumer one = getOne(wrapper);
        if (one != null) {
            throw new RuntimeException("用户名已存在");
        }

        // 2. 判断邮箱是否存在
        if(email!= null && email.length()>0) {
            wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Consumer::getEmail, email);
            one = getOne(wrapper);
            if (one != null) {
                throw new RuntimeException("邮箱已存在");
            }
        }else {
            consumer.setEmail(null);
        }
        // 3. 判断手机号是否存在
        if(phone!= null && phone.length()>0) {
            wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Consumer::getPhoneNum, phone);
            one = getOne(wrapper);
            if (one != null) {
                throw new RuntimeException("手机号已存在");
            }
        }else {
            consumer.setPhoneNum(null);
        }
        //注册用户
        consumer.setCreateTime(new Date());
        consumer.setUpdateTime(new Date());
        save(consumer);
    }


    @Override
    public Consumer login(Consumer consumer) {
        // 1. 同时将 username 和 password 在数据库中搜索
        LambdaQueryWrapper<Consumer> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Consumer::getUsername, consumer.getUsername());
        wrapper.eq(Consumer::getPassword, consumer.getPassword());
        Consumer one = getOne(wrapper);
        if (one == null) {
            throw new RuntimeException("用户名或密码错误");
        }
        return one;
    }
    @Override
    public Consumer loginByEmail(Consumer consumer) {
        LambdaQueryWrapper<Consumer> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Consumer::getEmail, consumer.getEmail());
        wrapper.eq(Consumer::getPassword, consumer.getPassword());

        Consumer one = getOne(wrapper);
        if (one == null) {
            throw new RuntimeException("邮箱或密码错误");
        }
        return one;
    }

    @Autowired
    private JavaMailSender sender;

    @Override
    public void sendVerificationCode(String email) {
        // 1. 校验邮箱是否存在
        LambdaQueryWrapper<Consumer> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Consumer::getEmail, email);
        Consumer one = getOne(wrapper);
        if (one == null) {
            throw new RuntimeException("邮箱不存在~");
        }

        // 邮件对象
        SimpleMailMessage mimeMessage = new SimpleMailMessage();

        // 发件人（必须和验证账号一致）
        mimeMessage.setFrom("1643489909@qq.com");

        // 收件人
        mimeMessage.setTo(email);

        // 标题
//
        mimeMessage.setSubject("YIN-MUSIC,找回密码...");

        // 内容（验证码）
        String code =System.currentTimeMillis() + "";
        code =code.substring(code.length() - 6);
        mimeMessage.setText("验证码："+ code);
        CacheUtils.setKey(email,code);
        System.out.println("验证码已缓存：" + CacheUtils.getKey(email));

        sender.send(mimeMessage);
    }
    @Override
    public void resetPassword(ResetPasswordDTO dto) {
        // 1. 验证码是否有效
        String email = dto.getEmail();
        String cacheCode = CacheUtils.getKey(email);
        if (cacheCode == null) {
            throw new RuntimeException("验证码已过期，请重新发送");
        }
        if (!cacheCode.equalsIgnoreCase(dto.getCode())) {
            throw new RuntimeException("验证码错误");
        }

        // 2. 两次密码是否一致
        String password = dto.getPassword();
        if (password == null || !password.equals(dto.getConfirmPassword())) {
            throw new RuntimeException("两次密码不一致");
        }

// 3. 执行修改密码的操作
        LambdaUpdateWrapper<Consumer> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Consumer::getEmail, email);
        wrapper.set(Consumer::getPassword, password);
        update(wrapper);

    }


}
