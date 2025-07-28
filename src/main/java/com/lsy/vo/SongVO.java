package com.lsy.vo;

import lombok.Data;

@Data
public class SongVO {
    private Integer id;
    private String url;
    private String name;
    private String introduction;
    private String singerName;
    private String pic;
    private String lyric;
}
