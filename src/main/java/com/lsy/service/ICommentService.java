package com.lsy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lsy.domain.Comment;
import com.lsy.vo.CommentVO;

import java.util.List;

public interface ICommentService extends IService<Comment> {

    List<CommentVO> type(Integer id, Integer type) ;

    void delete(Integer id);
}
