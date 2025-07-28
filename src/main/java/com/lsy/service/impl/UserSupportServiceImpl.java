package com.lsy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsy.domain.Comment;
import com.lsy.domain.UserSupport;
import com.lsy.mapper.UserSupportMapper;
import com.lsy.service.ICommentService;
import com.lsy.service.IConsumerService;
import com.lsy.service.IUserSupportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserSupportServiceImpl extends ServiceImpl<UserSupportMapper, UserSupport>
        implements IUserSupportService {

    @Autowired
    private ICommentService commentService;
    @Override
    public Integer test(UserSupport userSupport) {
        Integer result = 0;

        // 查询点赞记录
        LambdaQueryWrapper<UserSupport> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserSupport::getUserId, userSupport.getUserId());
        wrapper.eq(UserSupport::getCommentId, userSupport.getCommentId());
        UserSupport one = getOne(wrapper);

        // 查询 comment 记录
        LambdaQueryWrapper<Comment> wrapper1 = new LambdaQueryWrapper<>();
        wrapper1.eq(Comment::getId, userSupport.getCommentId());
        Comment one1 = commentService.getOne(wrapper1);

        // 判断是否点赞，还是取消点赞
        if (one == null) {
            // 点赞 user_support 新增一条记录
            save(userSupport);
            if (one1 != null) {
                // up + 1
                one1.setUp(one1.getUp() + 1);
                result =1;
                commentService.updateById(one1);
            }
        } else {
            // 取消点赞 user_support 删除对应记录
            removeById(one.getId());
            if (one1 != null) {
                // up - 1
                one1.setUp(one1.getUp() - 1);
                result =-1;
                commentService.updateById(one1);
            }
        }

        return result;
    }
}


