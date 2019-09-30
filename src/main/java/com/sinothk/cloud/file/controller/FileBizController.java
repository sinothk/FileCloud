package com.sinothk.cloud.file.controller;

import com.sinothk.base.entity.ResultData;
import com.sinothk.cloud.file.domain.FileBizEntity;
import com.sinothk.cloud.file.domain.FileSystemEntity;
import com.sinothk.cloud.file.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;

@Api(tags = "文件管理：业务文件")
@RestController
@RequestMapping("/file/biz")
public class FileBizController extends FileBaseController {

    @Resource(name = "fileBizService")
    private FileService<FileBizEntity> fileService;


    @ApiOperation(value = "新增：保存文件到Win", notes = "保存文件到Win")
    @PostMapping("/add")
//    @TokenCheck
    public ResultData<ArrayList<FileBizEntity>> add(
            @ApiParam(value = "应用AppId", type = "header", required = true) @RequestHeader(value = "appId") String appId,
            @ApiParam(value = "验证Token", type = "header", required = true) @RequestHeader(value = "token") String token,
            @ApiParam(value = "业务类型", required = true) @RequestParam("bizType") String bizType,
            @ApiParam(value = "文件类型", required = true) @RequestParam("fileType") String fileType,
            @ApiParam(value = "文件对象列表", required = true) @RequestParam("files") MultipartFile[] fileList) {

        //http://192.168.124.12:10002/file/add
        setService(fileService);
        return addFile(appId, token, bizType, fileType, fileList);
    }

//
//    @ApiOperation(value = "新增：保存文件到Linux", notes = "保存文件到Linux")
//    @PostMapping("/addByLinux")
//    @TokenCheck
//    public ResultData<ArrayList<FileEntity>> addByLinux(
//            @ApiParam(value = "应用AppId", type = "header", required = true) @RequestHeader(value = "appId") String appId,
//            @ApiParam(value = "验证Token", type = "header", required = true) @RequestHeader(value = "token") String token,
//            @ApiParam(value = "业务类型", required = true) @RequestParam("bizType") String bizType,
//            @ApiParam(value = "文件类型", required = true) @RequestParam("fileType") String fileType,
//            @ApiParam(value = "文件对象列表", required = true) @RequestParam("files") MultipartFile[] fileList) {
//        //http://192.168.124.12:10002/file/addByLinux
//        if (fileList == null || fileList.length == 0) {
//            return ResultData.error("文件对象不能为空");
//        }
//
//        if (StringUtil.isEmpty(bizType)) {
//            return ResultData.error("未填写文件业务类型");
//        }
//
//        if (StringUtil.isEmpty(appId)) {
//            return ResultData.error("appId不能为空");
//        }
//
//        String ownerName = TokenUtil.getUserName(token);
//
//        FileEntity fileEntity = new FileEntity();
//
//        fileEntity.setAppId(appId);
//        fileEntity.setOwnerUser(ownerName);
//        fileEntity.setFileType(fileType);
//        fileEntity.setBizType(bizType);
//
//        ArrayList<FileEntity> fileEntities = fileService.saveIntoLinux(fileList, fileEntity.getAppId(), fileEntity.getOwnerUser(), fileEntity.getFileType(), fileEntity.getBizType());
//        if (fileEntities == null) {
//            return ResultData.error("文件新增失败");
//        } else {
//            return ResultData.success(fileEntities);
//        }
//    }
//
//    @ApiOperation(value = "删除：根据Id删除文件", notes = "删除文件")
//    @DeleteMapping("/delById/{id}")
//    @TokenCheck
//    public ResultData<String> delById(
//            @ApiParam(value = "验证Token", type = "header", required = true) @RequestHeader(value = "token") String token
//            , @PathVariable String id) {
////        http://192.168.124.12:10002/file/delById/111
//        String errorMsg = fileService.delById(id);
//        if (StringUtil.isEmpty(errorMsg)) {
//            return ResultData.success("");
//        } else {
//            return ResultData.error(errorMsg);
//        }
//    }
//
//    @ApiOperation(value = "删除：根据bizId删除文件", notes = "根据bizId删除文件")
//    @DeleteMapping("/delByBizId")
//    @TokenCheck
//    public ResultData<String> delByBizId(
//            @ApiParam(value = "验证Token", type = "header", required = true) @RequestHeader(value = "token") String token
//            , @RequestParam("bizId") String bizId) {
////        http://192.168.124.12:10002/file/delByBizId
//        String errorMsg = fileService.delByBizId(bizId);
//        if (StringUtil.isEmpty(errorMsg)) {
//            return ResultData.success("");
//        } else {
//            return ResultData.error(errorMsg);
//        }
//    }
//
//    /**
//     * 查询单一业务所有文件数据
//     *
//     * @param token
//     * @param bizId
//     * @return
//     */
//    @ApiOperation(value = "查找：单一业务所有文件", notes = "查找：单一业务所有文件")
//    @GetMapping("/findFileByBizId")
//    @TokenCheck
//    public ResultData<ArrayList<FileEntity>> findFileByBizId(
//            @ApiParam(value = "验证Token", type = "header", required = true) @RequestHeader(value = "token") String token
//            , @RequestParam("bizId") String bizId) {
//
//        ArrayList<FileEntity> fileList = fileService.findFileByBizId(bizId);
//        return ResultData.success(fileList);
//    }
//
//    @ApiOperation(value = "查找：某用户所有文件", notes = "查找：某用户所有文件")
//    @GetMapping("/findFileByOwnerName")
//    @TokenCheck
//    public ResultData<IPage<FileEntity>> findFileByOwnerName(
//            @ApiParam(value = "验证Token", type = "header", required = true) @RequestHeader(value = "token") String token,
//            @RequestParam("currPage") int currPage,
//            @RequestParam("pageSize") int pageSize) {
//        String ownerName = TokenUtil.getUserName(token);
//
//        IPage<FileEntity> fileList = fileService.findFileByOwnerUser(ownerName, currPage, pageSize);
//
//        return ResultData.success(fileList);
//    }
}
