package com.sinothk.cloud.file.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sinothk.base.entity.ResultData;
import com.sinothk.base.utils.StringUtil;
import com.sinothk.cloud.file.config.ServerConfig;
import com.sinothk.cloud.file.domain.FileEntity;
import com.sinothk.cloud.file.repository.FileMapper;
import com.sinothk.cloud.file.service.FileService;
import com.sinothk.cloud.file.utils.FileManager;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("fileService")
public class FileServiceImpl implements FileService {

    @Resource(name = "serverConfig")
    private ServerConfig serverConfig;

    @Resource(name = "fileMapper")
    private FileMapper fileMapper;

//    @Override
//    public ResultData<FileEntity> addFiles(FileEntity fileEntity, MultipartFile[] files) {
//        try {
//            // 保存图片
//            Date currDate = new Date();
//            String dateFlag = new SimpleDateFormat("yyyyMM").format(currDate);// 时间目录
//            String photoId = String.valueOf(currDate.getTime());
//
//            ArrayList<MultipartFile> fileList = new ArrayList<>(Arrays.asList(files));
//
//            for (int i = 0; i < fileList.size(); i++) {
//
//                MultipartFile multipartFile = fileList.get(i);
//
//                // 目录
//                String ownerName = fileEntity.getOwnerUser();
//                String dirPath = ownerName + "/" + fileEntity.getFileType() + "/" + dateFlag + "/";
//                // 原文件名
//                String oldFileName = multipartFile.getOriginalFilename();
//                // 新文件名
//                String fileNameStr = ownerName + "_" + getIdByDateTimeString() + oldFileName.substring(oldFileName.lastIndexOf("."));
//
//                // 保存到硬件
//                String fileUrl = FileUtil.saveIntoWinOS(serverConfig.getVirtualPath(), dirPath, fileNameStr, multipartFile);
//
//                FileManager.getInstance().saveFile(serverConfig.getVirtualPath(), locPath, fileLocName, multipartFile);
//
//                if (i == 0 && !StringUtil.isEmpty(fileUrl)) {
//                    // 新增封面
//                    FileCoverEntity fileInfo = new FileCoverEntity();
//                    fileInfo.setFileCode(photoId);
//                    fileInfo.setFileOldName(oldFileName);
//                    fileInfo.setFileName(fileNameStr);
//                    fileInfo.setFileUrl(fileUrl);
//                    fileInfo.setFileSize(multipartFile.getSize());
//                    fileInfo.setCreateTime(currDate);
//                    fileInfo.setFileType(fileEntity.getFileType());
//                    fileInfo.setOwnerUser(ownerName);
//                    fileInfo.setBizType(fileEntity.getBizType());
//
//                    fileCoverMapper.insert(fileInfo);
//                }
//
//                // 新增图片文件
//                fileEntity.setFileCode(photoId);
//                fileEntity.setFileOldName(oldFileName);
//                fileEntity.setFileName(fileNameStr);
//                fileEntity.setFileUrl(fileUrl);
//                fileEntity.setFileSize(multipartFile.getSize());
//                fileEntity.setCreateTime(currDate);
//                fileEntity.setOwnerUser(ownerName);
//                fileMapper.insert(fileEntity);
//
//                Thread.sleep(1000);
//            }
//            return ResultData.success(null);
//        } catch (IllegalStateException | InterruptedException e) {
//            if (serverConfig.isDebug()) {
//                e.printStackTrace();
//            }
//            return ResultData.error("提交异常");
//        }
//    }

    /**
     * 删除文件：id
     *
     * @param id
     * @return
     */
    @Override
    public ResultData<Boolean> delFile(String id) {
        try {
            fileMapper.deleteById(id);
            return ResultData.success(true);
        } catch (Exception e) {
            if (serverConfig.isDebug()) {
                e.printStackTrace();
            }
            return ResultData.error("处理异常");
        }
    }

    /**
     * 删除业务数据
     *
     * @param fileEntity
     * @return
     */
    @Override
    public ResultData<Boolean> delFileByFileCode8Owner(FileEntity fileEntity) {
        try {
            // 删除文件
            QueryWrapper<FileEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(FileEntity::getFileCode, fileEntity.getFileCode())
                    .eq(FileEntity::getOwnerUser, fileEntity.getOwnerUser());
            fileMapper.delete(queryWrapper);
            return ResultData.success(true);
        } catch (Exception e) {
            if (serverConfig.isDebug()) {
                e.printStackTrace();
            }
            return ResultData.error("处理异常");
        }
    }

    /**
     * 获得业务文件
     *
     * @param fileEntity
     * @return
     */
    @Override
    public ResultData<List<FileEntity>> findFileByFileCodeAndOwner(FileEntity fileEntity) {
        try {
            QueryWrapper<FileEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda()
                    .eq(FileEntity::getFileCode, fileEntity.getFileCode())
                    .eq(FileEntity::getOwnerUser, fileEntity.getOwnerUser());

            List<FileEntity> fileList = fileMapper.selectList(queryWrapper);
            return ResultData.success(fileList);
        } catch (Exception e) {
            if (serverConfig.isDebug()) {
                e.printStackTrace();
            }
            return ResultData.error("处理异常");
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
    public ArrayList<FileEntity> saveIntoWin(MultipartFile[] files, String username, String fileType, String bizType) {
        try {
            //
            ArrayList<FileEntity> fileEntities = new ArrayList<>();
            // 保存
            Date currDate = new Date();
            String photoId = String.valueOf(currDate.getTime());

            for (int i = 0; i < files.length; i++) {
                MultipartFile multipartFile = files[i];

                // 新文件路径
                String fileServerPath = username + "/" + fileType + "/" + new SimpleDateFormat("yyyyMM").format(currDate) + "/";
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
                FileEntity fileEntity = new FileEntity();
                fileEntity.setFileCode(photoId);
                fileEntity.setFileName(fileTempName);
                fileEntity.setFileUrl(fileUrl);
                fileEntity.setFileSize(multipartFile.getSize());
                fileEntity.setCreateTime(currDate);
                fileEntity.setOwnerUser(username);
                fileEntity.setFileType(fileType);
                fileEntity.setBizType(bizType);
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
    public ArrayList<FileEntity> saveLinux(MultipartFile[] files, String username, String fileType, String fileName, String bizType) {
        return null;
    }

    public static String getIdByDateTimeString() {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }
}
