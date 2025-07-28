package com.lsy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lsy.common.R;
import com.lsy.domain.Consumer;
import com.lsy.dto.ResetPasswordDTO;
import com.lsy.query.BaseQuery;
import com.lsy.service.IConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/consumer")
public class ConsumerController {

    @Autowired // 注入
    private IConsumerService consumerService;

    @GetMapping("/list")
    public R list() {
        // 调用service接口
        List<Consumer> list = consumerService.list();
        // 响应数据
        return R.success("查询成功",list);
    }
//    分页需要参数（页码，每页记录），搜索参数，
@PostMapping("/page")
public R page(@RequestBody BaseQuery query) {
    IPage<Consumer> page = consumerService.pageConsumer(query);
    return R.success("查询成功",page);
}

//单个用户删除
    @DeleteMapping("/delete/{id}")
    public R delete(@PathVariable Integer id) {
        consumerService.removeById(id);
        return R.success("删除成功");
    }

//批量删除用户
@DeleteMapping("/batchDelete")
public R batchDelete(@RequestBody List<Integer> ids) {
    consumerService.removeBatchByIds(ids);
    return R.success("删除成功");
}

    @PostMapping("/avatar/{id}")
    public R avatarUpdate(@PathVariable Integer id, @RequestParam("file") MultipartFile file) {
        try {
            consumerService.avatarUpdate(id, file); // ✅ 真正调用 Service
            return R.success("头像更新成功");
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
    }

    //用户注册
        @PostMapping("/add")
        public R register(@RequestBody Consumer consumer) {
            // System.out.println(consumer);

            try {
                consumerService.register(consumer);
                return R.success("注册成功");
            } catch (Exception e) {
                e.printStackTrace();
                return R.error(e.getMessage());
            }
        }
    @PostMapping("/login")
    public R login(@RequestBody Consumer consumer) {
        try {
            Consumer one = consumerService.login(consumer);
            return R.success("登陆成功", one);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(e.getMessage());
        }
    }

//    邮箱登录
@PostMapping("/email")
public R loginByEmail(@RequestBody Consumer consumer) {
    try {
        Consumer one = consumerService.loginByEmail(consumer);
        return R.success("邮箱登录成功", one);
    } catch (Exception e) {
        e.printStackTrace();
        return R.error(e.getMessage());
    }

}

@GetMapping("/sendVerificationCode/{email}")
public R sendVerificationCode(@PathVariable String email){
        try{
           consumerService.sendVerificationCode(email);
            return R.success("发送成功");
        }catch (Exception e){
            e.printStackTrace();
            return R.error(e.getMessage());
        }
}

    @PostMapping("/resetPassword")
    public R resetPassword(@RequestBody ResetPasswordDTO dto) {
        try {
            consumerService.resetPassword(dto);
            return R.success("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(e.getMessage());
        }
    }


}

