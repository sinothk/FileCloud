package com.sinothk.cloud.file.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sinothk.base.utils.IdUtil;
import com.sinothk.base.utils.StringUtil;
import com.sinothk.cloud.file.config.ServerConfig;
import com.sinothk.cloud.file.domain.FileBaseEntity;
import com.sinothk.cloud.file.domain.FileBizEntity;
import com.sinothk.cloud.file.domain.FileVo;
import com.sinothk.cloud.file.repository.FileBizMapper;
import com.sinothk.cloud.file.service.FileService;
import com.sinothk.cloud.file.utils.FileManager;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Service("fileBizService")
public class FileBizServiceImpl implements FileService {

    @Resource(name = "serverConfig")
    private ServerConfig serverConfig;

    @Resource(name = "fileBizMapper")
    private FileBizMapper fileBizMapper;

//    /**
//     * 获得业务文件
//     *
//     * @param FileBizEntity
//     * @return
//     */
//    @Override
//    public ResultData<List<FileBizEntity>> findFileByFileCodeAndOwner(FileBizEntity FileBizEntity) {

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
            FileBizEntity fileBizEntity = fileBizMapper.selectById(id);

            // 删除表
            fileBizMapper.deleteById(id);
            // 删除硬件
            FileManager.getInstance().delFile(serverConfig.getVirtualPath() + fileBizEntity.getFileUrl());
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
            QueryWrapper<FileBizEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(FileBizEntity::getBizId, bizId);

            ArrayList<FileBizEntity> fileList = (ArrayList<FileBizEntity>) fileBizMapper.selectList(queryWrapper);

            for (FileBizEntity FileBizEntity : fileList) {
                fileBizMapper.deleteById(FileBizEntity.getId());
                FileManager.getInstance().delFile(serverConfig.getVirtualPath() + FileBizEntity.getFileUrl());
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
     * @param account
     * @param fileType
     * @param bizType
     * @return
     */
    @Override
    public ArrayList<FileBizEntity> saveIntoWin(MultipartFile[] files, String appId, String account, String fileType, String bizType) {
        try {
            //
            ArrayList<FileBizEntity> fileEntities = new ArrayList<>();
            // 保存
            Date currDate = new Date();
            String bizId = IdUtil.generateShortUuid();

            for (MultipartFile multipartFile : files) {
                // 新文件路径
                String fileServerPath = appId + "/" + account + "/" + fileType + "/" + new SimpleDateFormat("yyyyMM").format(currDate) + "/";
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
                FileBizEntity FileBizEntity = new FileBizEntity();
                FileBizEntity.setBizId(bizId);
                FileBizEntity.setFileName(fileTempName);
                FileBizEntity.setFileUrl(fileUrl);
                FileBizEntity.setFileSize(multipartFile.getSize());
                FileBizEntity.setCreateTime(currDate);
                FileBizEntity.setOwnerAccount(account);
                FileBizEntity.setFileType(fileType);
                FileBizEntity.setBizType(bizType);
                FileBizEntity.setAppId(appId);

                fileBizMapper.insert(FileBizEntity);

                fileEntities.add(FileBizEntity);
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
    public ArrayList<FileBizEntity> saveIntoLinux(MultipartFile[] files, String appId, String account, String fileType, String bizType) {
        try {
            //
            ArrayList<FileBizEntity> fileEntities = new ArrayList<>();
            // 保存
            Date currDate = new Date();
            String bizId = IdUtil.generateShortUuid();

            for (MultipartFile multipartFile : files) {
                // 新文件路径
                String fileServerPath = appId + "/" + account + "/" + fileType + "/" + new SimpleDateFormat("yyyyMM").format(currDate) + "/";
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
                FileBizEntity FileBizEntity = new FileBizEntity();
                FileBizEntity.setBizId(bizId);
                FileBizEntity.setFileName(fileTempName);
                FileBizEntity.setFileUrl(fileUrl);
                FileBizEntity.setFileSize(multipartFile.getSize());
                FileBizEntity.setCreateTime(currDate);
                FileBizEntity.setOwnerAccount(account);
                FileBizEntity.setFileType(fileType);
                FileBizEntity.setBizType(bizType);
                FileBizEntity.setAppId(appId);

                fileBizMapper.insert(FileBizEntity);

                fileEntities.add(FileBizEntity);
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
    public ArrayList<FileBizEntity> findFileByBizId(String bizId) {
        try {
            QueryWrapper<FileBizEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(FileBizEntity::getBizId, bizId);
            return (ArrayList<FileBizEntity>) fileBizMapper.selectList(queryWrapper);
        } catch (Exception e) {
            if (serverConfig.isDebug()) {
                e.printStackTrace();
            }
            return new ArrayList<>();
        }
    }

    @Override
    public IPage<FileBizEntity> findFileByOwnerUser(FileVo vo, int currPage, int pageSize) {
        try {
            return fileBizMapper.selectFilePageList(new Page<>(currPage, pageSize), vo);
        } catch (Exception e) {
            if (serverConfig.isDebug()) {
                e.printStackTrace();
            }
            return new Page<>(currPage, pageSize);
        }
    }
}
