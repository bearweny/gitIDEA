package com.lsy.query;

import lombok.Data;

import java.util.List;

@Data
public class CollectQuery {
    private Integer userId;
    private List<Integer> songIds;
    private String keyword;

  
}
