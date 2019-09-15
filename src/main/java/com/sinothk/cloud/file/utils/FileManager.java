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
     * @param locFilePath E:/SINOTHK/serverVMFiles/sinothk/liangyt/img/201907/
     * @param fileName    liangyt_20190716112744.zip
     * @param file
     * @return
     * @throws IOException
     */
    public String saveFileIntoWin(String locFilePath, String fileName, MultipartFile file) {
        if (file.isEmpty()) {
            return null;
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

        return locFilePath + fileName;

//        new Thread(() -> {
//
//            String allPath = virtualPath + locPath;
//
//            File fp = new File(allPath);
//
//            if (!fp.exists()) {
//                fp.mkdirs();
//            }
//
//            Path path = fp.toPath().resolve(fileName);
//
//            try {
//                Files.copy(file.getInputStream(), path);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }).start();
    }

//    public String saveIntoWin(String filePath, String fileName, MultipartFile file) {
//        if (file.isEmpty()) {
//            return null;
//        }
//
//        File fp = new File(filePath);
//
//        if (!fp.exists()) {
//            fp.mkdirs();
//        }
//
//        Path path = fp.toPath().resolve(fileName);
//
//        try {
//            Files.copy(file.getInputStream(), path);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return filePath + fileName;
//    }

    public boolean isImage(String fileName) {
        if (fileName.contains("png")
                || fileName.contains("PNG")
                || fileName.contains("jpg")
                || fileName.contains("JPG")
                || fileName.contains("jpeg")
                || fileName.contains("JPEG")) {
            return true;
        } else {
            return false;
        }
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
}
