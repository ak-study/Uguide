package com.chinafight.gongxiangdaoyou.config;

import org.apache.commons.httpclient.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: ak
 * @date: 2020/6/11 16:22
 * @description:
 */
@Configuration
@ConfigurationProperties(prefix = "elasticsearch")
@EnableConfigurationProperties
public class WebConfig extends WebMvcConfigurationSupport {
    private String schema;

    /** 集群地址，如果有多个用“,”隔开 */
    private String address;

    /** 连接超时时间 */
    private int connectTimeout;

    /** Socket 连接超时时间 */
    private int socketTimeout;

    /** 获取连接的超时时间 */
    private int connectionRequestTimeout;

    /** 最大连接数 */
    private int maxConnectNum;

    /** 最大路由连接数 */
    private int maxConnectPerRoute;

    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
            "classpath:/META-INF/resources/", "classpath:/resources/",
            "classpath:/static/", "classpath:/public/" };


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (!registry.hasMappingForPattern("/webjars/**")) {
            registry.addResourceHandler("/webjars/**").addResourceLocations(
                    "classpath:/META-INF/resources/webjars/");
        }
        if (!registry.hasMappingForPattern("/**")) {
            registry.addResourceHandler("/**").addResourceLocations(
                    CLASSPATH_RESOURCE_LOCATIONS);
        }

    }
//
//    @Bean(name = "restHighLevelClient")
//    public RestHighLevelClient restHighLevelClient() {
//        // 拆分地址
//        List<HttpHost> hostLists = new ArrayList<>();
//        String[] hostList = address.split(",");
//        for (String addr : hostList) {
//            String host = addr.split(":")[0];
//            String port = addr.split(":")[1];
//            hostLists.add(new HttpHost(host, Integer.parseInt(port), schema));
//        }
//        // 转换成 HttpHost 数组
//        HttpHost[] httpHost = hostLists.toArray(new HttpHost[]{});
//        // 构建连接对象
//        RestClientBuilder builder = RestClient.builder(httpHost);
//        // 异步连接延时配置
//        builder.setRequestConfigCallback(requestConfigBuilder -> {
//            requestConfigBuilder.setConnectTimeout(connectTimeout);
//            requestConfigBuilder.setSocketTimeout(socketTimeout);
//            requestConfigBuilder.setConnectionRequestTimeout(connectionRequestTimeout);
//            return requestConfigBuilder;
//        });
//        // 异步连接数配置
//        builder.setHttpClientConfigCallback(httpClientBuilder -> {
//            httpClientBuilder.setMaxConnTotal(maxConnectNum);
//            httpClientBuilder.setMaxConnPerRoute(maxConnectPerRoute);
//            return httpClientBuilder;
//        });
//        return new RestHighLevelClient(builder);
//    }
}
