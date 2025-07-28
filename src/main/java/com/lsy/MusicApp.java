package com.lsy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Scanner;

@SpringBootApplication
@RestController
@MapperScan("com.lsy.mapper")
public class MusicApp {
   @GetMapping("/hello")
public String hello(){
    return "hello spring boot";
}

public static void main(String[] args)
{
SpringApplication.run(MusicApp.class);
}
}
