package com.chinafight.gongxiangdaoyou.controller;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: ak
 * @date: 2021/3/4 10:10
 * @description:
 */
@RestController
@RequestMapping("dataSource")
public class DataSourceTest {
    @Autowired
    SqlSessionFactory sqlSessionFactory;

    @GetMapping("test1")
    public String test1() throws InterruptedException {
        Configuration configuration = sqlSessionFactory.getConfiguration();
        System.out.println(Thread.currentThread().getName());
        return "success";
    }

    @GetMapping("test2")
    public String test2() throws InterruptedException {
        System.out.println(Thread.currentThread().getName());
        return "success";
    }
}
