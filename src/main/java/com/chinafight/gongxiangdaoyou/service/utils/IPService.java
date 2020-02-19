package com.chinafight.gongxiangdaoyou.service.utils;

import com.chinafight.gongxiangdaoyou.eunm.CustomerEnum;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

@Service
public class IPService {

    public HashMap<Object, Object> getAddrName(String IP) throws JSONException, IOException{

        JSONObject json = readJsonFromUrl("http://api.map.baidu.com/location/ip?ak=e1kpO8igvj8ji1hS6eFlfvXb1StqQhz2&ip="+IP);
        /* 获取到的json对象：
         *         {"address":"CN|河北|保定|None|UNICOM|0|0",
         *        "content":{"address_detail":{"province":"河北省","city":"保定市","street":"","district":"","street_number":"","city_code":307},
         *        "address":"河北省保定市","point":{"x":"12856963.35","y":"4678360.5"}},
         *        "status":0}
         */
        //如果IP是本地127.0.0.1或者内网IP192.168则status分别返回1和2
        HashMap<Object, Object> map = new HashMap<>();
        String status = json.opt("status").toString();
        if(!"0".equals(status)){
            if (status.equals("1001") || status.equals("1002")) return CustomerEnum.NORMAL_STATUS.getMsgMap("内网访问");
            return CustomerEnum.ERROR_STATUS.getMsgMap();
        }
        JSONObject content=json.getJSONObject("content");//获取json对象里的content对象
        JSONObject addr_detail=content.getJSONObject("address_detail");//从content对象里获取address_detail
        map.put("province",addr_detail.optString("province"));
        map.put("city",addr_detail.optString("city"));
        map.put("address",addr_detail.optString("address"));
        map.put("point",addr_detail.optString("point"));
        return map;
    }

    private static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        try (InputStream is = new URL(url).openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));//Charset.forName("UTF-8")
            String jsonText = readAll(rd);
            // System.out.println(json);
            return new JSONObject(jsonText);
        }
        //关闭输入流
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 ||"unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 ||"unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 ||"unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;

    }
}
