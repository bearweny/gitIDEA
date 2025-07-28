package com.lsy.query;

import lombok.Data;

@Data
public class BaseQuery {
    private Integer currentPage;
    private Integer pageSize;
    private String keyword;
}
