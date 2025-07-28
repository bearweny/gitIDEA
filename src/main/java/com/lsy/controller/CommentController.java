package com.lsy.controller;

import com.lsy.common.R;
import com.lsy.domain.Comment;
import com.lsy.service.ICommentService;
import com.lsy.vo.CommentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private ICommentService commentService;

    @GetMapping("/type/{id}/{type}")
    public R type(@PathVariable Integer id, @PathVariable Integer type) {
        // 调用 service 接口
        List<CommentVO> list = commentService.type(id, type);
        // 响应数据
        return R.success("查询成功", list);
    }

    @PostMapping("/add")
    public R add(@RequestBody Comment comment) {
        System.out.println(comment);
        comment.setCreateTime(new Date());
        commentService.save(comment);
        return R.success("添加成功");
    }


    @DeleteMapping("/delete/{id}")
    public R delete(@PathVariable Integer id) {
        commentService.delete(id);
        return R.success("删除成功");
    }


}


