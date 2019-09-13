package com.sinothk.cloud.file.service;

import com.sinothk.base.entity.ResultData;
import com.sinothk.cloud.file.domain.FileCoverEntity;
import com.sinothk.cloud.file.domain.FileEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public interface FileService {

    ResultData<Boolean> delFile(String id);

    ResultData<Boolean> delFileByFileCode8Owner(FileEntity fileCode);

    ResultData<List<FileEntity>> findFileByFileCodeAndOwner(FileEntity fileEntity);

    ResultData<FileCoverEntity> findFileCover(FileEntity fileEntity);

    ResultData<List<FileCoverEntity>> findFileCoverByOwner(String ownerName);

    ArrayList<FileEntity> save(MultipartFile[] files, String username, String fileType, String fileName, String bizType);

    ArrayList<FileEntity> saveLinux(MultipartFile[] files, String username, String fileType, String fileName, String bizType);
}
