package com.sinothk.cloud.file.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

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
     * @param virtualPath E:/SINOTHK/serverVMFiles/sinothk/
     * @param locPath     liangyt/img/201907/
     * @param fileNewName liangyt_20190716112744.zip
     * @param file
     * @return
     * @throws IOException
     */
    public void saveFile(String virtualPath, String locPath, String fileNewName, MultipartFile file){
        if (file.isEmpty()) {
            return;
        }

//        String allPath = virtualPath + locPath;
//        File fp = new File(allPath);
//        if (!fp.exists()) {
//            fp.mkdirs();
//        }
//
//        Path path = fp.toPath().resolve(fileNewName);
//        try {
//            Files.copy(file.getInputStream(), path);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        new Thread(() -> {
            String allPath = virtualPath + locPath;
            File fp = new File(allPath);
            if (!fp.exists()) {
                fp.mkdirs();
            }

            Path path = fp.toPath().resolve(fileNewName);
            try {
                Files.copy(file.getInputStream(), path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
