package com.lsy.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("comment")
public class Comment {
    @TableId(type = IdType.AUTO)
    private Integer id;            // 评论主键
    private Integer userId;        // 用户 ID
    private Integer songId;        // 歌曲 ID（type = 0 时有效）
    private Integer songListId;    // 歌单 ID（type = 1 时有效）
    private String content;        // 评论内容
    private Date createTime;       // 创建时间
    private Integer type;          // 评论类型（0=歌曲，1=歌单）
    private Integer up;            // 点赞数量
}