package com.sinothk.cloud.file.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

public interface FileService<T> {

    String delById(String id);

    String delByBizId(String bizId);

    ArrayList<T> saveIntoWin(MultipartFile[] files,String appId, String username, String fileType, String bizType);

    ArrayList<T> saveIntoLinux(MultipartFile[] files,String appId, String username, String fileType, String bizType);

    ArrayList<T> findFileByBizId(String bizId);

    IPage<T> findFileByOwnerUser(String ownerUser, int currPage, int pageSize);
}
