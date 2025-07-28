package com.lsy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lsy.domain.Comment;
import com.lsy.vo.CommentVO;

import java.util.List;

public interface CommentMapper extends BaseMapper<Comment> {
    List<CommentVO> type(Integer id, Integer type);
}
