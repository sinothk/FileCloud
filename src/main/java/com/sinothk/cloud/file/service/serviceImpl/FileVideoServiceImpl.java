package com.sinothk.cloud.file.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sinothk.base.entity.PageData;
import com.sinothk.base.utils.IdUtil;
import com.sinothk.cloud.file.config.ServerConfig;
import com.sinothk.cloud.file.domain.FileVideoEntity;
import com.sinothk.cloud.file.domain.FileVo;
import com.sinothk.cloud.file.repository.FileVideoMapper;
import com.sinothk.cloud.file.service.FileService;
import com.sinothk.cloud.file.utils.FfmpegUtil;
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

    /**
     * 删除文件：id
     *
     * @param id
     * @return
     */
    @Override
    public String delById(String id) {
        try {
            return delFile(fileMapper.selectById(id));
        } catch (Exception e) {
            if (serverConfig.isDebug()) {
                e.printStackTrace();
            }
            return e.getMessage();
        }
    }

    private String delFile(FileVideoEntity fileEntity) {
        if (fileEntity == null) {
            return "没有相应数据";
        }
        // 删除表
        fileMapper.deleteById(fileEntity.getId());
        // 删除硬件
        FileManager.getInstance().delFile(serverConfig.getVirtualPath() + fileEntity.getFileUrl());
        FileManager.getInstance().delFile(serverConfig.getVirtualPath() + fileEntity.getFileCover());
        return "删除成功";
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

            if (fileList == null || fileList.size() == 0) {
                return "没有相应数据";
            }

            for (FileVideoEntity fileEntity : fileList) {
//                // 删除表
//                fileMapper.deleteById(fileEntity.getId());
//                // 删除硬件
//                FileManager.getInstance().delFile(serverConfig.getVirtualPath() + fileEntity.getFileUrl());
//                FileManager.getInstance().delFile(serverConfig.getVirtualPath() + fileEntity.getFileCover());
                return delFile(fileEntity);
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
    public ArrayList<FileVideoEntity> saveIntoWin(MultipartFile[] files, String appId, String account, String fileType, String bizType) {
        try {
            //
            ArrayList<FileVideoEntity> fileEntities = new ArrayList<>();
            // 保存
            Date currDate = new Date();
            String bizId = IdUtil.generateShortUuid();

            for (MultipartFile multipartFile : files) {
                // 新文件路径
                String fileServerPath = appId + "/" + account + "/" + fileType + "/" + new SimpleDateFormat("yyyyMM").format(currDate) + "/";
                // 原文件名
                String fileName = multipartFile.getOriginalFilename();

                /*
                 *  ============ 保存到硬件
                 */
                // 拼装相对地址
                String locFilePath = serverConfig.getVirtualPath() + fileServerPath;
                // 判断文件名,存在，则重新命名
                String fileTempName = FileManager.getInstance().getFileName(locFilePath, fileName);

                // 保存文件：文件存储的磁盘路径
                FileManager.getInstance().saveFileIntoWin(locFilePath, fileTempName, multipartFile);

                // 保存封面：
                String videoLocFileAllPath = locFilePath + fileTempName;

                String coverFileName = fileTempName + ".png";
                String coverFileAllPath = locFilePath + coverFileName;

                String dbCoverPath = fileServerPath + coverFileName;

                // 保存文件访问相对地址
                String fileUrl = fileServerPath + fileTempName;

                // 保存到表
                FileVideoEntity fileEntity = new FileVideoEntity();
                fileEntity.setBizId(bizId);
                fileEntity.setFileName(fileTempName);
                fileEntity.setFileUrl(fileUrl);
                fileEntity.setFileSize(multipartFile.getSize());
                fileEntity.setCreateTime(currDate);
                fileEntity.setOwnerAccount(account);
                fileEntity.setFileType(fileType);
                fileEntity.setBizType(bizType);
                fileEntity.setAppId(appId);

                fileEntity.setFileCover(dbCoverPath);

                fileMapper.insert(fileEntity);

                fileEntities.add(fileEntity);

                saveVideoCoverFile(videoLocFileAllPath, coverFileAllPath);
            }
            return fileEntities;

        } catch (IllegalStateException e) {
            if (serverConfig.isDebug()) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private void saveVideoCoverFile(String videoFileAllPath, String coverFileAllPath) {
        new Thread(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            FfmpegUtil.getImgInWin(videoFileAllPath, serverConfig.getFfmpegPath(), coverFileAllPath);
        }).start();
    }

    @Override
    public ArrayList<FileVideoEntity> saveIntoLinux(MultipartFile[] files, String appId, String account, String fileType, String bizType) {
        try {
            //
            ArrayList<FileVideoEntity> fileEntities = new ArrayList<>();
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

                // 保存封面：
                String videoLocFileAllPath = locFilePath + fileTempName;

                String coverFileName = fileTempName + ".png";
                String coverFileAllPath = locFilePath + coverFileName;

                String dbCoverPath = fileServerPath + coverFileName;

                // 保存文件访问相对地址
                String fileUrl = fileServerPath + fileTempName;

                // 保存到表
                FileVideoEntity fileEntity = new FileVideoEntity();
                fileEntity.setBizId(bizId);
                fileEntity.setFileName(fileTempName);
                fileEntity.setFileUrl(fileUrl);
                fileEntity.setFileSize(multipartFile.getSize());
                fileEntity.setCreateTime(currDate);
                fileEntity.setOwnerAccount(account);
                fileEntity.setFileType(fileType);
                fileEntity.setBizType(bizType);
                fileEntity.setAppId(appId);

                fileEntity.setFileCover(dbCoverPath);

                fileMapper.insert(fileEntity);

                fileEntities.add(fileEntity);

                getVideoCoverFileInLinux(videoLocFileAllPath, coverFileAllPath);
            }
            return fileEntities;

        } catch (IllegalStateException e) {
            if (serverConfig.isDebug()) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private void getVideoCoverFileInLinux(String videoFileAllPath, String coverFileAllPath) {
        new Thread(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            FfmpegUtil.getImgInLinux(videoFileAllPath, serverConfig.getFfmpegPath(), coverFileAllPath);
        }).start();
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
    public IPage<FileVideoEntity> findFileByOwnerUser(FileVo vo, int currPage, int pageSize) {
        try {

//            Page page = new Page<>(currPage, pageSize);
//
//            IPage<FileVideoEntity> pageResult = fileMapper.selectFilePageList(page, vo);
//
//            PageData pageData = new PageData();
//            pageData.setHasNext(page.hasNext());

            return fileMapper.selectFilePageList(new Page<>(currPage, pageSize), vo);
        } catch (Exception e) {
            if (serverConfig.isDebug()) {
                e.printStackTrace();
            }
            return new Page<>(currPage, pageSize);
        }
    }
}
