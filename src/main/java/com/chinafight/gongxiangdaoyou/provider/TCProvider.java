package com.chinafight.gongxiangdaoyou.provider;

import com.chinafight.gongxiangdaoyou.controller.UtilsController;
import com.chinafight.gongxiangdaoyou.service.utils.IPService;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.GeneratePresignedUrlRequest;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.transfer.TransferManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

@Component
public class TCProvider {
    @Value("${TP.secretId}")
    String secretId;
    @Value("${TP.secretKey}")
    String secretKey;

    public String upLoad(MultipartFile file) throws IOException {
        String path = UtilsController.class.getResource("/").getPath();
        String filePath = path + "static/img/" + file.getOriginalFilename();
        System.out.println(filePath);
        file.transferTo(new File(filePath));
        return "img/" + file.getOriginalFilename();
    }

    public void deleteObject() {

    }
}
