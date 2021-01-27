package com.chinafight.gongxiangdaoyou.service.utils;

import com.chinafight.gongxiangdaoyou.eunm.CustomerEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

@Slf4j
@Service
public class IPService {

    public HashMap<Object, Object> getAddrName(String IP) throws JSONException, IOException{
        HashMap<Object, Object> map = new HashMap<>(16);
        map.put("province","福建");
        map.put("city","福州市连江县");
        return map;
    }

    public String getAddrJson(String Ip){
        JSONObject json = null;
        try {
            json = readJsonFromUrl("http://api.map.baidu.com/location/ip?ak=vgrSAB4UtprjZHvfPP9yxpLfR73IqysG&ip="+Ip);
            return com.alibaba.fastjson.JSONObject.toJSONString(json);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        try (InputStream is = new URL(url).openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String jsonText = readAll(rd);
            rd.close();
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

    public static void getPosition(String latitude, String longitude) throws MalformedURLException {
        BufferedReader in = null;
        URL tirc = new URL("http://api.map.baidu.com/geocoder/v2/?callback=renderReverse&location=" + latitude + "," + longitude
                + "&output=json&pois=1&ak=" + "wws9Qu73jw4QkOL6osEyIsA9Yob2yYgR");
        try {
            in = new BufferedReader(new InputStreamReader(tirc.openStream(), StandardCharsets.UTF_8));
            String res;
            StringBuilder sb = new StringBuilder("");
            while ((res = in.readLine()) != null) {
                sb.append(res.trim());
            }
            String str = sb.toString();
            System.out.println(str);
            if (!StringUtils.isEmpty(str)) {
                int lngStart = str.indexOf("formatted_address\":\"");
                int lngEnd = str.indexOf("\",\"business");
                if (lngStart > 0 && lngEnd > 0 ) {
                    String ads = str.substring(lngStart + 20, lngEnd);
                    System.out.println("ads:" + ads);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
