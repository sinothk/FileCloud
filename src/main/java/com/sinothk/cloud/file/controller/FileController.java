package com.sinothk.cloud.file.controller;

import com.sinothk.base.entity.ResultData;
import com.sinothk.base.utils.JWTUtil;
import com.sinothk.base.utils.StringUtil;
import com.sinothk.base.utils.TokenUtil;
import com.sinothk.cloud.file.domain.FileEntity;
import com.sinothk.cloud.comm.authorization.TokenCheck;
import com.sinothk.cloud.file.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Api(tags = "文件系统")
@RestController
@RequestMapping("/file")
public class FileController extends FileWinController {

    @Resource(name = "fileService")
    private FileService fileService;

    @ApiOperation(value = "新增：保存图片文件(Linux)", notes = "新增：保存图片文件(Linux)")
    @PostMapping("/addLinux")
    @TokenCheck
    public ResultData<ArrayList<FileEntity>> addLinux(
            @ApiParam(value = "验证Token", type = "header", required = true) @RequestHeader(value = "token") String token,
            @ApiParam(value = "业务类型", required = true) @RequestParam("bizType") String bizType,
            @ApiParam(value = "文件类型", required = true) @RequestParam("fileType") String fileType,
            @ApiParam(value = "文件对象列表", required = true) @RequestParam("files") MultipartFile[] fileList) {
        //http://192.168.124.12:7000/file/addLinux

        if (fileList == null || fileList.length == 0) {
            return ResultData.error("文件对象不能为空");
        }

        if (StringUtil.isEmpty(bizType)) {
            return ResultData.error("未填写文件业务类型");
        }

        FileEntity fileEntity = new FileEntity();
        fileEntity.setOwnerUser("oo");
        fileEntity.setFileType(fileType);
        fileEntity.setFileName("hello.png");
        fileEntity.setBizType(bizType);

        ArrayList<FileEntity> fileEntities = new ArrayList<>();//fileService.save(fileList, fileEntity.getOwnerUser(), fileEntity.getFileType(), fileEntity.getFileName(), fileEntity.getBizType());
        if (fileEntities == null) {
            return ResultData.error("文件新增失败");
        } else {
            return ResultData.success(fileEntities);
        }
    }

    @ApiOperation(value = "删除：根据Id删除文件", notes = "删除文件")
    @DeleteMapping("/delById/{id}")
    @TokenCheck
    public ResultData<Boolean> delFileById(
            @ApiParam(value = "验证Token", type = "header", required = true) @RequestHeader(value = "token") String token
            , @PathVariable String id) {
//        http://192.168.124.12:10002/file/delById/111
        return fileService.delFile(id);
    }

    @ApiOperation(value = "删除：根据fileCode删除业务文件", notes = "删除业务文件")
    @DeleteMapping("/delByCode/{fileCode}")
    @Transactional// 事务回滚
    @TokenCheck
    public ResultData<Boolean> delFileByFileCode(
            @ApiParam(value = "验证Token", type = "header", required = true) @RequestHeader(value = "token") String token
            , @ApiParam(value = "fileCode", required = true) @PathVariable String fileCode) {

        String username = JWTUtil.getUsername(token);

        FileEntity fileEntity = new FileEntity();
        fileEntity.setFileCode(fileCode);
        fileEntity.setOwnerUser(username);

        return fileService.delFileByFileCode8Owner(fileEntity);
    }

    @ApiOperation(value = "查找：业务文件", notes = "查找：业务文件")
    @GetMapping("/findFilesByFileCode")
    @TokenCheck
    public ResultData<List<FileEntity>> findFilesByFileCode(
            @ApiParam(value = "验证Token", type = "header", required = true) @RequestHeader(value = "token") String token
            , @RequestParam("fileCode") String fileCode) {

        FileEntity fileEntity = new FileEntity();
        fileEntity.setFileCode(fileCode);
        fileEntity.setOwnerUser(JWTUtil.getUsername(token));

        return fileService.findFileByFileCodeAndOwner(fileEntity);
    }
}
