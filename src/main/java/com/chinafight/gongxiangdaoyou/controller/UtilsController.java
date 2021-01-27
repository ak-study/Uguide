package com.chinafight.gongxiangdaoyou.controller;

import com.chinafight.gongxiangdaoyou.cache.CacheTags;
import com.chinafight.gongxiangdaoyou.cache.ProfileTag;
import com.chinafight.gongxiangdaoyou.eunm.CustomerEnum;
import com.chinafight.gongxiangdaoyou.mapper.utils.ImgMapper;
import com.chinafight.gongxiangdaoyou.model.ImgModel;
import com.chinafight.gongxiangdaoyou.provider.TCProvider;
import com.chinafight.gongxiangdaoyou.service.utils.IPService;
import com.chinafight.gongxiangdaoyou.utils.CodeUtil;
import com.chinafight.gongxiangdaoyou.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.RenderedImage;
import java.io.*;
import java.net.URL;
import java.util.*;

@RequestMapping()
@RestController
@Slf4j
public class UtilsController {
    @Autowired
    CodeUtil codeUtil;
    @Autowired
    TCProvider tcProvider;
    @Autowired
    ImgMapper imgMapper;
    @Autowired
    IPService ipService;

    @GetMapping("getCode")
    public Object getImg() throws IOException {
        return null;
    }

    @GetMapping("getAddr")
    public Object getAddr(HttpServletRequest request) throws IOException, JSONException {
        String ipAddr = ipService.getIpAddr(request);
        System.out.println(ipAddr);
        HashMap<Object, Object> cityMap = ipService.getAddrName(ipAddr);
        cityMap.put("ip地址",ipAddr);
        return cityMap;
    }

    @PostMapping("upload")
    public Object upLoadFile(MultipartFile file) throws IOException {
        return tcProvider.upLoad(file);
    }

    @PostMapping("uploadBanner")
    public Object upLoadBanner(MultipartFile file) throws IOException{
        tcProvider.upLoad(file);
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

}
