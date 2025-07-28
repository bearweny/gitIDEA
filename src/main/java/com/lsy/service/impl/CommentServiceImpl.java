package com.lsy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsy.domain.Comment;
import com.lsy.domain.UserSupport;
import com.lsy.mapper.CommentMapper;
import com.lsy.service.ICommentService;
import com.lsy.service.IUserSupportService;
import com.lsy.vo.CommentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
        implements ICommentService {
    @Autowired
    private IUserSupportService supportService;
    @Override
    public List<CommentVO> type(Integer id, Integer type){
        return baseMapper.type(id,type);
    }

    @Override
    public void delete(Integer id) {
        // 1. 删除评论点赞
        LambdaQueryWrapper<UserSupport> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserSupport::getCommentId, id);
        supportService.remove(wrapper);

        // 2. 删除评论本身
        removeById(id);
    }


}
