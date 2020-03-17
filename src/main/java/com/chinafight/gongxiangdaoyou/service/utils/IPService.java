package com.chinafight.gongxiangdaoyou.service.utils;

import com.chinafight.gongxiangdaoyou.eunm.CustomerEnum;
import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.naming.ldap.Rdn;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

@Service
public class IPService {

    public HashMap<Object, Object> getAddrName(String IP) throws JSONException, IOException{

        JSONObject json = readJsonFromUrl("http://api.map.baidu.com/location/ip?ak=wws9Qu73jw4QkOL6osEyIsA9Yob2yYgR&ip="+IP);
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
            if (status.equals("1001") || status.equals("1002")){//内网访问
                map.put("city","泉州市");
                return map;
            }
            return CustomerEnum.ERROR_STATUS.getMsgMap();
        }
        JSONObject content=json.getJSONObject("content");//获取json对象里的content对象
        JSONObject addr_detail=content.getJSONObject("address_detail");//从content对象里获取address_detail
        JSONObject point = content.getJSONObject("point");
        map.put("province",addr_detail.optString("province"));
        map.put("city",addr_detail.optString("city"));
        map.put("x",point.optString("x"));
        map.put("y",point.optString("y"));
        return map;
    }

    public Object getCoorsByIp(String x,String y) throws IOException, JSONException {
        String location=x+","+y;
        URL url = new URL("http://api.map.baidu.com/geocoder/v2/?ak=wws9Qu73jw4QkOL6osEyIsA9Yob2yYgR&location="+location+"&output=json&pois=0");
        JSONObject jsonObject = readJsonFromUrl(url.toString());
        System.out.println(jsonObject.toString());
        return null;
    }

    private static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        try (InputStream is = new URL(url).openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));//Charset.forName("UTF-8")
            String jsonText = readAll(rd);
            // System.out.println(json);
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
            in = new BufferedReader(new InputStreamReader(tirc.openStream(), "UTF-8"));
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
