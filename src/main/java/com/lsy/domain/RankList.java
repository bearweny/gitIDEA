package com.lsy.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("rank_list")
public class RankList {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer songListId;
    private Integer consumerId;
    private Integer score;
}
