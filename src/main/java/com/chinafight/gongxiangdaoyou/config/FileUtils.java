package com.chinafight.gongxiangdaoyou.config;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author: ak
 * @date: 2021/1/7 14:12
 * @description:
 */
public class FileUtils {
    public static void writeContent(String data) {
        BufferedWriter bw = null;

        File file = new File("content.txt");
        try {
            if(!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file.getName(), true);
            bw = new BufferedWriter(fileWriter);
            bw.write(data);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(bw != null) {
                    bw.flush();
                    bw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
