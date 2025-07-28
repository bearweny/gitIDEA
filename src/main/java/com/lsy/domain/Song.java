package com.lsy.domain;



import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("song")
public class Song {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("singer_id")
    private Integer singerId;

    private String name;

    private String introduction;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_time")
    private Date updateTime;

    private String pic;

    private String lyric;

    private String url;
}

