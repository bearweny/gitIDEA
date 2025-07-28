package com.lsy.config;

import com.lsy.common.Constants;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebPicConfig implements WebMvcConfigurer {

    // TODO 这个配置类的目的是干什么，就是注册了一个类似于拦截器吧，看到对应的资源 会将其...（注释未完）

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/avatorImages/**")
                .addResourceLocations(Constants.AVATOR_IMAGES_FILE_PATH);
//        System.out.println("映射路径为: " + Constants.AVATOR_IMAGES_FILE_PATH);
//        System.out.println("头像路径已注册: " + Constants.AVATOR_IMAGES_FILE_PATH);

        registry.addResourceHandler("/img/singerPic/**")
                .addResourceLocations(Constants.SINGER_IMAGES_FILE_PATH);
        registry.addResourceHandler("/img/songPic/**")
                .addResourceLocations(Constants.SONG_IMAGES_FILE_PATH);

        registry.addResourceHandler("/song/**")
                .addResourceLocations(Constants.SONG_PATH_FILE_PATH);

        registry.addResourceHandler("/img/songListPic/**")
                .addResourceLocations(Constants.SONG_LIST_IMAGES_FILE_PATH);

        registry.addResourceHandler("/img/swiper/**")
                .addResourceLocations(Constants.SWIPER_LIST_IMAGES_FILE_PATH);
    }
}
