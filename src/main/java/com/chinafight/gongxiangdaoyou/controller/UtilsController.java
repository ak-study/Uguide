package com.chinafight.gongxiangdaoyou.controller;

import com.chinafight.gongxiangdaoyou.cache.CacheTags;
import com.chinafight.gongxiangdaoyou.cache.ProfileTag;
import com.chinafight.gongxiangdaoyou.eunm.CustomerEnum;
import com.chinafight.gongxiangdaoyou.provider.TCProvider;
import com.chinafight.gongxiangdaoyou.utils.CodeUtil;
import com.chinafight.gongxiangdaoyou.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.RenderedImage;
import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
        File file = new File("C:\\Users\\10424\\Desktop" + System.currentTimeMillis() + ".jpg");
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

    @PostMapping("upload")
    public Object upLoadFile(MultipartFile file) throws IOException {
        File f = null;
        URL url;
        if(file.equals("")||file.getSize()<=0){
            file = null;
        }else{
            InputStream ins = file.getInputStream();
            f=new File(Objects.requireNonNull(file.getOriginalFilename()));
            Utils.inputStreamToFile(ins,f);
            ins.close();
            url = tcProvider.upLoad(f);
            Utils.deleteTempFile(f);//删除临时文件
            return url;
        }
        return CustomerEnum.ERROR_NULL_POINT.getMsgMap();
    }

    @GetMapping("getUserTags")
    public Object getUserTags(){
        List<ProfileTag> userTag = CacheTags.get(1, null);
        return userTag;
    }

    @GetMapping("getGuideTags")
    public Object getGuideTags(){
        return CacheTags.get(2, null);
    }
}
