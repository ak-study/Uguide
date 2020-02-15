package com.chinafight.gongxiangdaoyou.controller;

import com.chinafight.gongxiangdaoyou.cache.CacheTags;
import com.chinafight.gongxiangdaoyou.cache.ProfileTag;
import com.chinafight.gongxiangdaoyou.eunm.CustomerEnum;
import com.chinafight.gongxiangdaoyou.mapper.utils.ImgMapper;
import com.chinafight.gongxiangdaoyou.model.ImgModel;
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
import java.util.*;

@Controller
@RestController
public class UtilsController {
    @Autowired
    CodeUtil codeUtil;

    @Autowired
    TCProvider tcProvider;
    @Autowired
    ImgMapper imgMapper;

    @GetMapping("getCode")
    public Object getImg() throws IOException {
        HashMap<Object, Object> msgMap = new HashMap<>();
        File file = new File("/" + System.currentTimeMillis() + ".jpg");
        OutputStream out = new FileOutputStream(file);
        Map<String, Object> map = CodeUtil.generateCodeAndPic();
        ImageIO.write((RenderedImage) map.get("codePic"), "jpeg", out);
        URL url = tcProvider.upLoad(file);
        //生成默认头像地址
//        URL url1 = tcProvider.upLoad(new File("C://Users/10424/Desktop/code/111.png"));
//        System.out.println(url1);
        Utils.deleteFile(file.getPath());
        msgMap.put("url", url);
        msgMap.put("value", map.get("code"));
        msgMap.put("status", CustomerEnum.NORMAL_STATUS.getMsgMap());
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

    @PostMapping("uploadBanner")
    public Object upLoadBanner(MultipartFile file) throws IOException{
        if (file.isEmpty()){
            return CustomerEnum.ERROR_NULL_POINT.getMsgMap();
        }
        File f = null;
        URL url;
        InputStream ins = file.getInputStream();
        f=new File(Objects.requireNonNull(file.getOriginalFilename()));
        Utils.inputStreamToFile(ins,f);
        ins.close();
        url = tcProvider.upLoad(f);
        imgMapper.insertImg(1,url+"","banner"+System.currentTimeMillis());
        return CustomerEnum.NORMAL_USER_INSERT.getMsgMap();
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

    @GetMapping("getBanner")
    public Object getBanner(){
        HashMap<Object, Object> map = new HashMap<>();

        List<ImgModel> banner = imgMapper.getImg("banner", 1);
        ArrayList<String> bannerList = new ArrayList<>();
        //每次最新的四个获取四个轮播图
        for (int i=banner.size()-1;i>banner.size()-5;i--){
            ImgModel imgModel = banner.get(i);
            bannerList.add(imgModel.getImg());
        }
        map.put("data",bannerList);
        map.put("status",CustomerEnum.NORMAL_STATUS.getMsgMap());
        return map;
    }

    @GetMapping("getFlushBanner")
    public Object getFlushBanner(){
        URL url1 = tcProvider.upLoad(new File("C:\\Users\\10424\\Desktop\\code\\banner1.jpg"));
        URL url2 = tcProvider.upLoad(new File("C:\\Users\\10424\\Desktop\\code\\banner2.jpg"));
        URL url3 = tcProvider.upLoad(new File("C:\\Users\\10424\\Desktop\\code\\banner3.jpg"));
        URL url4 = tcProvider.upLoad(new File("C:\\Users\\10424\\Desktop\\code\\banner4.jpg"));
        HashMap<Object, Object> map = new HashMap<>();
        map.put("url1",url1);
        map.put("url2",url2);
        map.put("url3",url3);
        map.put("url4",url4);
        return map;
    }

}
