package com.chinafight.gongxiangdaoyou.provider;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.transfer.TransferManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.UUID;
@Component
public class TCProvider {
    @Value("${TP.secretId}")
    String secretId;
    @Value("${TP.secretKey}")
    String secretKey;

    public URL upLoad(File file) {
        String fileName = file.getName();
        URL url = null;
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        Region region = new Region("ap-guangzhou");
        ClientConfig clientConfig = new ClientConfig(region);
        COSClient cosClient = new COSClient(cred, clientConfig);
        try {
            String bucketName = "coummunity-1300724762";
            String newFileName=fileName+UUID.randomUUID()+".jpg";
            //上传文件
            PutObjectResult putObjectResult =cosClient.putObject(bucketName,newFileName,file);
            //获取文件下载地址
            if (putObjectResult!=null){
                String key="/"+newFileName;
                url = cosClient.generatePresignedUrl(bucketName, key, new Date(new Date().getTime() + 5 * 60 * 10000 * 300));
            }
        } catch (CosServiceException serverException) {
            serverException.printStackTrace();
            return null;
        } catch (CosClientException clientException) {
            clientException.printStackTrace();
            return null;
        }
        return url;
    }
}
