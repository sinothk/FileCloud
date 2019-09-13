package com.sinothk.cloud.file.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sinothk.base.entity.ResultData;
import com.sinothk.cloud.file.config.ServerConfig;
import com.sinothk.cloud.file.domain.FileCoverEntity;
import com.sinothk.cloud.file.domain.FileEntity;
import com.sinothk.cloud.file.repository.FileCoverMapper;
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
    @Resource(name = "fileCoverMapper")
    private FileCoverMapper fileCoverMapper;

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

            // 删除封面
            QueryWrapper<FileCoverEntity> queryWrapperCover = new QueryWrapper<>();
            queryWrapperCover.lambda().eq(FileCoverEntity::getFileCode, fileEntity.getFileCode())
                    .eq(FileCoverEntity::getOwnerUser, fileEntity.getOwnerUser());
            fileCoverMapper.delete(queryWrapperCover);

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
     * 获得业务封面文件
     *
     * @param fileEntity
     * @return
     */
    @Override
    public ResultData<FileCoverEntity> findFileCover(FileEntity fileEntity) {
        try {
            QueryWrapper<FileCoverEntity> queryWrapperCover = new QueryWrapper<>();
            queryWrapperCover.lambda().eq(FileCoverEntity::getFileCode, fileEntity.getFileCode())
                    .eq(FileCoverEntity::getOwnerUser, fileEntity.getOwnerUser());

            FileCoverEntity fileCoverList = fileCoverMapper.selectOne(queryWrapperCover);
            return ResultData.success(fileCoverList);
        } catch (Exception e) {
            if (serverConfig.isDebug()) {
                e.printStackTrace();
            }
            return ResultData.error("处理异常");
        }
    }

    @Override
    public ResultData<List<FileCoverEntity>> findFileCoverByOwner(String ownerName) {
        try {
            QueryWrapper<FileCoverEntity> queryWrapperCover = new QueryWrapper<>();
            queryWrapperCover.lambda()
                    .eq(FileCoverEntity::getOwnerUser, ownerName);
            List<FileCoverEntity> fileCoverList = fileCoverMapper.selectList(queryWrapperCover);
            return ResultData.success(fileCoverList);
        } catch (Exception e) {
            if (serverConfig.isDebug()) {
                e.printStackTrace();
            }
            return ResultData.error("处理异常");
        }
    }

    @Override
    public ArrayList<FileEntity> save(MultipartFile[] files, String username, String fileType, String fileName, String bizType) {
        try {
            //
            ArrayList<FileEntity> fileEntities = new ArrayList<>();
            // 保存
            Date currDate = new Date();
            String photoId = String.valueOf(currDate.getTime());

            for (int i = 0; i < files.length; i++) {

                MultipartFile multipartFile = files[i];

                // 原文件名
                String oldFileName = multipartFile.getOriginalFilename();
                // 新文件名
                String fileClass = oldFileName.substring(oldFileName.lastIndexOf("."));
                String fileLocName = username + "_" + getIdByDateTimeString() + fileClass;

                // 相对目录
                String locPath = username + "/" + fileType + "/" + new SimpleDateFormat("yyyyMM").format(currDate) + "/";

                String fileUrl = locPath + fileLocName;

                // 保存到硬件
                FileManager.getInstance().saveFile(serverConfig.getVirtualPath(), locPath, fileLocName, multipartFile);

                // 新增图片文件
                FileEntity fileEntity = new FileEntity();
                fileEntity.setFileCode(photoId);
                fileEntity.setFileOldName(oldFileName);
                fileEntity.setFileName(fileName);
                fileEntity.setFileLocName(fileLocName);
                fileEntity.setFileUrl(fileUrl);
                fileEntity.setFileSize(multipartFile.getSize());
                fileEntity.setCreateTime(currDate);
                fileEntity.setOwnerUser(username);
                fileEntity.setFileType(fileType);
                fileEntity.setBizType(bizType);
                fileMapper.insert(fileEntity);
                fileEntities.add(fileEntity);

                if (i == 0) {// 新增封面
                    if (fileType.equals("IMAGE") && FileManager.getInstance().isImage(oldFileName)) {
                        // 图片处理方式
                        FileCoverEntity fileInfo = new FileCoverEntity();
                        fileInfo.setFileCode(photoId);
                        fileInfo.setFileOldName(oldFileName);
                        fileInfo.setFileName(fileName);
                        fileInfo.setFileLocName(fileLocName);
                        fileInfo.setFileUrl(fileUrl);
                        fileInfo.setFileSize(multipartFile.getSize());
                        fileInfo.setCreateTime(currDate);
                        fileInfo.setFileType(fileType);
                        fileInfo.setOwnerUser(username);
                        fileInfo.setBizType(bizType);

                        fileCoverMapper.insert(fileInfo);
                    }

                }
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
