package com.sinothk.cloud.file.service;

import com.sinothk.cloud.file.domain.FileEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

public interface FileService {

    String delById(String id);

    String delByBizId(String bizId);

    ArrayList<FileEntity> saveIntoWin(MultipartFile[] files, String username, String fileType, String bizType);

    ArrayList<FileEntity> saveIntoLinux(MultipartFile[] files, String username, String fileType, String bizType);

    ArrayList<FileEntity> findFielByBizId(String bizId);
}
