package com.sinothk.cloud.file.controller;

import com.sinothk.base.entity.ResultData;
import com.sinothk.base.utils.StringUtil;
import com.sinothk.base.utils.TokenUtil;
import com.sinothk.cloud.comm.authorization.TokenCheck;
import com.sinothk.cloud.file.domain.FileEntity;
import com.sinothk.cloud.file.service.FileService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;

public class FileWinController {

    @Resource(name = "fileService")
    private FileService fileService;

    @ApiOperation(value = "新增：保存文件", notes = "新增文件")
    @PostMapping("/add")
    @TokenCheck
    public ResultData<ArrayList<FileEntity>> add(
            @ApiParam(value = "验证Token", type = "header", required = true) @RequestHeader(value = "token") String token,
            @ApiParam(value = "业务类型", required = true) @RequestParam("bizType") String bizType,
            @ApiParam(value = "文件类型", required = true) @RequestParam("fileType") String fileType,
            @ApiParam(value = "文件对象列表", required = true) @RequestParam("files") MultipartFile[] fileList) {
        //http://192.168.124.12:10002/file/add
        if (fileList == null || fileList.length == 0) {
            return ResultData.error("文件对象不能为空");
        }

        if (StringUtil.isEmpty(bizType)) {
            return ResultData.error("未填写文件业务类型");
        }

        String ownerName = TokenUtil.getUserName(token);

        FileEntity fileEntity = new FileEntity();
        fileEntity.setOwnerUser(ownerName);
        fileEntity.setFileType(fileType);
        fileEntity.setBizType(bizType);

        ArrayList<FileEntity> fileEntities = fileService.saveIntoWin(fileList, fileEntity.getOwnerUser(), fileEntity.getFileType(), fileEntity.getBizType());
        if (fileEntities == null) {
            return ResultData.error("文件新增失败");
        } else {
            return ResultData.success(fileEntities);
        }
    }
}
