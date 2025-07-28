package com.lsy.common;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


public class Constants {

    // 获取项目根目录（D:\IDEL UI\idel project\music20257）
    public static final String ASSETS_PATH = System.getProperty("user.dir").replace("\\", "/");

    // 相对路径（用于数据库保存字段）
    public static final String AVATOR_IMAGES = "/img/avatorImages/";

    // 文件上传保存路径（如：D:/.../img/avatorImages/）
    public static final String AVATOR_IMAGES_FILE = ASSETS_PATH + AVATOR_IMAGES;

    // Spring 资源映射路径（file:D:/.../img/avatorImages/）
    public static final String AVATOR_IMAGES_FILE_PATH = "file:" + AVATOR_IMAGES_FILE;

    public static final String SINGER_IMAGES = "/img/singerPic/";


    public static final String SINGER_IMAGES_FILE = ASSETS_PATH + SINGER_IMAGES;


    public static final String SINGER_IMAGES_FILE_PATH = "file:" + SINGER_IMAGES_FILE;



    public static final String SONG_IMAGES = "/img/songPic/";


    public static final String SONG_IMAGES_FILE = ASSETS_PATH + SONG_IMAGES;


    public static final String SONG_IMAGES_FILE_PATH = "file:" + SONG_IMAGES_FILE;


    public static final String SONG_PATH = "/song/";


    public static final String SONG_PATH_FILE = ASSETS_PATH + SONG_PATH;


    public static final String SONG_PATH_FILE_PATH = "file:" + SONG_PATH_FILE;

    // 歌单图片的访问路径
    public static String SONG_LIST_IMAGES = "/img/songListPic/";

    // 项目的根路径 + 歌单图片存放路径
    public static String SONG_LIST_IMAGES_FILE = ASSETS_PATH + SONG_LIST_IMAGES;

    // file: + 项目的根路径 + 歌单图片存放路径（资源映射用）
    public static String SONG_LIST_IMAGES_FILE_PATH = "file:" + SONG_LIST_IMAGES_FILE;

    //=========================>轮播图片路径
    //存储数据库路径
    public static String SWIPER_LIST_IMAGES = "/img/swiper/";
    //项目的根路径 + 歌单图片存放路径
    public static String SWIPER_LIST_IMAGES_FILE = ASSETS_PATH + SWIPER_LIST_IMAGES;
    // file: + 项目的根路径 + 歌单图片存放路径
    public static String SWIPER_LIST_IMAGES_FILE_PATH = "file:" + SWIPER_LIST_IMAGES_FILE;

}





