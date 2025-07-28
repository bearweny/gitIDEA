package com.lsy.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user_support")
public class UserSupport {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer commentId;
    private Integer userId;

}
