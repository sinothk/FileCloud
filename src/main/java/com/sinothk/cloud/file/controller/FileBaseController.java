package com.sinothk.cloud.file.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sinothk.base.entity.ResultData;
import com.sinothk.base.utils.StringUtil;
import com.sinothk.base.utils.TokenUtil;
import com.sinothk.cloud.file.domain.FileVo;
import com.sinothk.cloud.file.service.FileService;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

public class FileBaseController {

    private FileService fileService;

    void setService(FileService fileService) {
        this.fileService = fileService;
    }

    ResultData<ArrayList> addFile(String appId, String token, String bizType, String fileType, MultipartFile[] fileList) {

        if (fileList == null || fileList.length == 0) {
            return ResultData.error("文件对象不能为空");
        }

        if (StringUtil.isEmpty(bizType)) {
            return ResultData.error("未填写文件业务类型");
        }

        if (StringUtil.isEmpty(appId)) {
            return ResultData.error("appId不能为空");
        }

        String ownerName = TokenUtil.getUserName(token);
        ArrayList fileEntities = fileService.saveIntoWin(fileList, appId, ownerName, fileType, bizType);

        if (fileEntities == null) {
            return ResultData.error("文件新增失败");
        } else {
            return ResultData.success(fileEntities);
        }
    }

    ResultData addFileByLinux(String appId, String token, String bizType, String fileType, MultipartFile[] fileList) {
        //http://192.168.124.12:10002/file/addByLinux
        if (fileList == null || fileList.length == 0) {
            return ResultData.error("文件对象不能为空");
        }

        if (StringUtil.isEmpty(bizType)) {
            return ResultData.error("未填写文件业务类型");
        }

        if (StringUtil.isEmpty(appId)) {
            return ResultData.error("appId不能为空");
        }

        String ownerName = TokenUtil.getUserName(token);

        ArrayList fileEntities = fileService.saveIntoLinux(fileList, appId, ownerName, fileType, bizType);
        if (fileEntities == null) {
            return ResultData.error("文件新增失败");
        } else {
            return ResultData.success(fileEntities);
        }
    }

    ResultData<String> delById(String id) {
        String errorMsg = fileService.delById(id);
        if (StringUtil.isEmpty(errorMsg)) {
            return ResultData.success("");
        } else {
            return ResultData.error(errorMsg);
        }
    }

    ResultData<String> delByBizId(String bizId) {
        String errorMsg = fileService.delByBizId(bizId);
        if (StringUtil.isEmpty(errorMsg)) {
            return ResultData.success("");
        } else {
            return ResultData.error(errorMsg);
        }
    }

    ResultData findFilesByBizId(String bizId) {

        ArrayList fileList = fileService.findFileByBizId(bizId);
        return ResultData.success(fileList);
    }

    ResultData findFileListByOwnerName(String token, String fileType, int currPage, int pageSize) {
        String ownerName = TokenUtil.getUserName(token);

        FileVo vo = new FileVo();
        vo.setOwnerUser(ownerName);
        vo.setFileType(fileType);
        IPage fileList = fileService.findFileByOwnerUser(vo, currPage, pageSize);

        return ResultData.success(fileList);
    }
}
