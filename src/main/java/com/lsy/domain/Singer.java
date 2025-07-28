package com.lsy.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
@TableName("singer")
public class Singer {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String name;

    private Integer sex;

    private String pic;
@JsonFormat(pattern = "yyy-MM-dd")
    private Date birth;

    private String location;

    private String introduction;
}
