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
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class TCProvider {
    @Value("${project-path}")
    String projectPath;

    public String upLoad(MultipartFile file) throws IOException {
        String fileName = null;
        String path = null;
        try {
            if (file == null || file.isEmpty() || file.getSize() <= 0) {
                throw new IllegalArgumentException();
            }
            path = getClass().getClassLoader().getResource("static/img/").toURI().getPath();
            File dir = new File(path);
            if (!dir.getParentFile().exists()) {
                boolean mkdir = dir.getParentFile().mkdir();
                log.info("目录创建:{}", mkdir);
            }
            fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            String filePath = path  + fileName;
            log.info("保存的路径{}", path + fileName);

            File toFile = new File(filePath);
            file.transferTo(toFile);
        } catch (Exception e) {
            log.error("文件上传错误", e);
        }
        return projectPath + fileName;
    }
}
