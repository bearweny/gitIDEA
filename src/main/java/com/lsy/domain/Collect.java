package com.lsy.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("collect")
public class Collect {
@TableId(type= IdType.AUTO)
    private Integer id;
    private Integer userId;
    private Integer type;
    private Integer songId;
    private Integer songListId;
    private Date createTime;
}
