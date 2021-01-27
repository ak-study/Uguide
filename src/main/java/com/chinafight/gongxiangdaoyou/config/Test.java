package com.chinafight.gongxiangdaoyou.config;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.IOException;

/**
 * @author: ak
 * @date: 2021/1/7 14:11
 * @description:
 */
public class Test {
    private static Integer count = 0;

    public static void main(String[] args) {
        HttpClient httpClient = new HttpClient();
        try {
            for (int i = 0; i < 4000; i++) {
                System.out.println("开始爬取第" + (1 + i) + "页评论。");
                getMessage(httpClient, "800760067", i + 1, 2);
            }
            System.out.println("一共有 " + count + " 个lsp;");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 抓数据，浏览器中找到bilibili评论接口为 https://api.bilibili.com/x/v2/reply
     * 几个主要请求参数：
     * type:评论类型。这里固定值 1
     * oid:哪个视频
     * pn:第几页的评论
     * sort:排序。0:按照时间排序。2：按照热度排序。默认2
     */
    public static void getMessage(HttpClient httpClient, String oid, int pn,
                                  int sort) {
        try {
            //请求接口地址
            String biliUrl = "https://api.bilibili.com/x/v2/reply?&type=1&oid=" + oid + "&pn=" + pn + "&sort=" + sort;
            GetMethod getMethod = new GetMethod(biliUrl);
            PostMethod postMethod = new PostMethod();
            //设置请求头
            postMethod.setRequestHeader("user-agent", "postMethod.setRequestHeader(\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.70 Safari/537.36");
            httpClient.executeMethod(getMethod);
            //获取到接口返回值
            String text = getMethod.getResponseBodyAsString();
            getMethod.abort();
            JSONObject jsonObject = JSONObject.parseObject(text);
            for (int i = 0; i < jsonObject.size(); i++) {
                String name = (String) JSONPath.eval(jsonObject, "$.data.replies[" + i + "].member.uname");
                String sex = (String) JSONPath.eval(jsonObject, "$.data.replies[" + i + "].member.sex");
                String message = (String) JSONPath.eval(jsonObject, "$.data.replies[" + i + "].content.message");
                if (message == null || !message.contains("老婆")) {
                    continue;
                }
                count++;
                FileUtils.writeContent("用户：" + name + "，性别：" + sex + "，评论:" + message);
            }
        } catch (Exception ignored) {
        }
    }


}
