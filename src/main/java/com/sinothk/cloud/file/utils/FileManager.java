package com.sinothk.cloud.file.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileManager {

    private static FileManager fileManager;

    public static FileManager getInstance() {
        if (fileManager == null) {                // Single Checked
            synchronized (FileManager.class) {
                if (fileManager == null) {        // Double checked
                    fileManager = new FileManager();
                }
            }
        }
        return fileManager;
    }

    /**
     * 保存到Windows中
     *
     * @param locFilePath E:/SINOTHK/serverVMFiles/sinothk/liangyt/img/201907/
     * @param fileName    liangyt_20190716112744.zip
     * @param file        文件
     */
    public void saveFileIntoWin(String locFilePath, String fileName, MultipartFile file) {
        new Thread(() -> {

            if (file.isEmpty()) {
                return;
            }

            File fp = new File(locFilePath);

            if (!fp.exists()) {
                fp.mkdirs();
            }

            Path path = fp.toPath().resolve(fileName);

            try {
                Files.copy(file.getInputStream(), path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * 保存文件到Linux系统
     *
     * @param locFilePath /usr/***
     * @param fileName    liangyt_20190716112744.zip
     * @param file        文件
     */
    public void saveFileIntoLinux(String locFilePath, String fileName, MultipartFile file) {
        new Thread(() -> {
            if (file.isEmpty()) {
                return;
            }

            File newFile = new File(locFilePath + fileName);
            if (!newFile.exists()) {
                boolean isOk = newFile.mkdirs();
            }

            try {
                file.transferTo(newFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * 判断文件：存在同名文件处理后返回
     *
     * @param locPath
     * @param fileName
     * @return
     */
    public String getFileName(String locPath, String fileName) {
        File tempFile = new File(locPath + fileName);
        if (tempFile.exists()) {
            String fileNameBefore = fileName.substring(0, fileName.lastIndexOf("."));
            String fileClass = fileName.substring(fileName.lastIndexOf("."));
            fileName = fileNameBefore + "_" + new Date().getTime() + fileClass;
            return fileName;
        } else {
            return fileName;
        }
    }

    /**
     * 删除文件
     *
     * @param fileAllPath
     */
    public void delFile(String fileAllPath) {
        new Thread(() -> {
            try {
                File file = new File(fileAllPath);
                if (file.exists()) {
                    boolean del = file.delete();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }


}
