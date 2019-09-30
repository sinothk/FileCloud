package com.sinothk.cloud.file.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sinothk.base.utils.IdUtil;
import com.sinothk.cloud.file.config.ServerConfig;
import com.sinothk.cloud.file.domain.FileVideoEntity;
import com.sinothk.cloud.file.repository.FileVideoMapper;
import com.sinothk.cloud.file.service.FileService;
import com.sinothk.cloud.file.utils.FileManager;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Service("fileVideoService")
public class FileVideoServiceImpl implements FileService {

    @Resource(name = "serverConfig")
    private ServerConfig serverConfig;

    @Resource(name = "fileVideoMapper")
    private FileVideoMapper fileMapper;

//    /**
//     * 获得业务文件
//     *
//     * @param fileEntity
//     * @return
//     */
//    @Override
//    public ResultData<List<FileEntity>> findFileByFileCodeAndOwner(FileEntity fileEntity) {

//    }

    /**
     * 删除文件：id
     *
     * @param id
     * @return
     */
    @Override
    public String delById(String id) {
        try {
            FileVideoEntity fileEntity = fileMapper.selectById(id);

            // 删除表
            fileMapper.deleteById(id);
            // 删除硬件
            FileManager.getInstance().delFile(serverConfig.getVirtualPath() + fileEntity.getFileUrl());
            return "";
        } catch (Exception e) {
            if (serverConfig.isDebug()) {
                e.printStackTrace();
            }
            return e.getMessage();
        }
    }

    /**
     * 删除文件：bizId
     *
     * @param bizId
     * @return
     */
    @Override
    public String delByBizId(String bizId) {

        try {
            QueryWrapper<FileVideoEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(FileVideoEntity::getBizId, bizId);

            ArrayList<FileVideoEntity> fileList = (ArrayList<FileVideoEntity>) fileMapper.selectList(queryWrapper);

            for (FileVideoEntity fileEntity : fileList) {
                fileMapper.deleteById(fileEntity.getId());
                FileManager.getInstance().delFile(serverConfig.getVirtualPath() + fileEntity.getFileUrl());
            }
            return "";
        } catch (Exception e) {
            if (serverConfig.isDebug()) {
                e.printStackTrace();
            }
            return e.getMessage();
        }
    }

    /**
     * 保存文件到windows
     *
     * @param files
     * @param username
     * @param fileType
     * @param bizType
     * @return
     */
    @Override
    public ArrayList<FileVideoEntity> saveIntoWin(MultipartFile[] files,String appId, String username, String fileType, String bizType) {
        try {
            //
            ArrayList<FileVideoEntity> fileEntities = new ArrayList<>();
            // 保存
            Date currDate = new Date();
            String bizId = IdUtil.generateShortUuid();

            for (MultipartFile multipartFile : files) {
                // 新文件路径
                String fileServerPath = appId + "/" + username + "/" + fileType + "/" + new SimpleDateFormat("yyyyMM").format(currDate) + "/";
                // 原文件名
                String fileName = multipartFile.getOriginalFilename();

                // 保存到硬件
                // 拼装相对地址
                String locFilePath = serverConfig.getVirtualPath() + fileServerPath;
                // 判断文件名,存在，则重新命名
                String fileTempName = FileManager.getInstance().getFileName(locFilePath, fileName);
                // 返回文件存储的磁盘路径
                FileManager.getInstance().saveFileIntoWin(locFilePath, fileTempName, multipartFile);

                // 保存文件访问相对地址
                String fileUrl = fileServerPath + fileTempName;

                // 保存到表
                FileVideoEntity fileEntity = new FileVideoEntity();
                fileEntity.setBizId(bizId);
                fileEntity.setFileName(fileTempName);
                fileEntity.setFileUrl(fileUrl);
                fileEntity.setFileSize(multipartFile.getSize());
                fileEntity.setCreateTime(currDate);
                fileEntity.setOwnerUser(username);
                fileEntity.setFileType(fileType);
                fileEntity.setBizType(bizType);
                fileEntity.setAppId(appId);

                fileMapper.insert(fileEntity);

                fileEntities.add(fileEntity);
            }
            return fileEntities;

        } catch (IllegalStateException e) {
            if (serverConfig.isDebug()) {
                e.printStackTrace();
            }
            return null;
        }
    }

    @Override
    public ArrayList<FileVideoEntity> saveIntoLinux(MultipartFile[] files, String appId, String username, String fileType, String bizType) {
        try {
            //
            ArrayList<FileVideoEntity> fileEntities = new ArrayList<>();
            // 保存
            Date currDate = new Date();
            String bizId = IdUtil.generateShortUuid();

            for (MultipartFile multipartFile : files) {
                // 新文件路径
                String fileServerPath = appId + "/" + username + "/" + fileType + "/" + new SimpleDateFormat("yyyyMM").format(currDate) + "/";
                // 原文件名
                String fileName = multipartFile.getOriginalFilename();

                // 保存到硬件
                // 拼装相对地址
                String locFilePath = serverConfig.getVirtualPath() + fileServerPath;
                // 判断文件名,存在，则重新命名
                String fileTempName = FileManager.getInstance().getFileName(locFilePath, fileName);
                // 返回文件存储的磁盘路径
                FileManager.getInstance().saveFileIntoLinux(locFilePath, fileTempName, multipartFile);

                // 保存文件访问相对地址
                String fileUrl = fileServerPath + fileTempName;

                // 保存到表
                FileVideoEntity fileEntity = new FileVideoEntity();
                fileEntity.setBizId(bizId);
                fileEntity.setFileName(fileTempName);
                fileEntity.setFileUrl(fileUrl);
                fileEntity.setFileSize(multipartFile.getSize());
                fileEntity.setCreateTime(currDate);
                fileEntity.setOwnerUser(username);
                fileEntity.setFileType(fileType);
                fileEntity.setBizType(bizType);
                fileEntity.setAppId(appId);

                fileMapper.insert(fileEntity);

                fileEntities.add(fileEntity);
            }
            return fileEntities;

        } catch (IllegalStateException e) {
            if (serverConfig.isDebug()) {
                e.printStackTrace();
            }
            return null;
        }
    }

    @Override
    public ArrayList<FileVideoEntity> findFileByBizId(String bizId) {
        try {
            QueryWrapper<FileVideoEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(FileVideoEntity::getBizId, bizId);
            return (ArrayList<FileVideoEntity>) fileMapper.selectList(queryWrapper);
        } catch (Exception e) {
            if (serverConfig.isDebug()) {
                e.printStackTrace();
            }
            return new ArrayList<>();
        }
    }

    @Override
    public IPage<FileVideoEntity> findFileByOwnerUser(String ownerUser, int currPage, int pageSize) {
        try {
            Page<FileVideoEntity> page = new Page<>(currPage, pageSize);

            QueryWrapper<FileVideoEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(FileVideoEntity::getOwnerUser, ownerUser);

            return fileMapper.selectPage(page, queryWrapper);
        } catch (Exception e) {
            if (serverConfig.isDebug()) {
                e.printStackTrace();
            }
            return new Page<>(currPage, pageSize);
        }
    }
}