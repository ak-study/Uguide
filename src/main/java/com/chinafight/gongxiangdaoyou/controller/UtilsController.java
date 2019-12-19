package com.chinafight.gongxiangdaoyou.controller;

import com.chinafight.gongxiangdaoyou.eunm.CustomerEnum;
import com.chinafight.gongxiangdaoyou.provider.TCProvider;
import com.chinafight.gongxiangdaoyou.utils.CodeUtil;
import com.chinafight.gongxiangdaoyou.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.RenderedImage;
import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Controller
@RestController
public class UtilsController {
    @Autowired
    CodeUtil codeUtil;

    @Autowired
    TCProvider tcProvider;


    @GetMapping("getCode")
    public Object getImg() throws IOException {
        HashMap<Object, Object> msgMap = new HashMap<>();
        File file = new File("F://img/" + System.currentTimeMillis() + ".jpg");
        OutputStream out = new FileOutputStream(file);
        Map<String, Object> map = CodeUtil.generateCodeAndPic();
        ImageIO.write((RenderedImage) map.get("codePic"), "jpeg", out);
        URL url = tcProvider.upLoad(file);
//        URL url1 = tcProvider.upLoad(new File("C://Users/K/Desktop/111.png"));
        Utils.deleteFile(file.getName());///上传完成后删除文件
        msgMap.put("url", url);
        msgMap.put("value", map.get("code"));
        msgMap.put("status", CustomerEnum.NORMAL_STATUS.getMsgMap());
//        msgMap.put("默认头像",url1);
        return msgMap;
    }

    @GetMapping("getAddr")
    public Object getAddr(HttpServletRequest request){
        HashMap<Object, Object> map = new HashMap<>();
        Object city = request.getSession().getAttribute("city");
        Object ip = request.getSession().getAttribute("ip");
        map.put("city",city);
        map.put("ip",ip);
        map.put("status", CustomerEnum.NORMAL_STATUS.getMsgMap());
        return map;
    }


}
