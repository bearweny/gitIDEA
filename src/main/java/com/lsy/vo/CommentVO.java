package com.lsy.vo;

import lombok.Data;

@Data
public class CommentVO {
    // 评论id
    private Integer id;

    // 用户名
    private String username;

    // 评论内容
    private String content;
//    头像
    private String avator;
//    评论时间
    private String createTime;

    private Integer up;

    private Integer userId;
}

