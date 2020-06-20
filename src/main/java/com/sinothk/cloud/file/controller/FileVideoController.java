package com.sinothk.cloud.file.controller;

import com.sinothk.base.entity.PageData;
import com.sinothk.base.entity.ResultData;
import com.sinothk.cloud.comm.authorization.TokenCheck;
import com.sinothk.cloud.file.domain.FileVideoEntity;
import com.sinothk.cloud.file.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;

@Api(tags = "文件管理：视频文件")
@RestController
@RequestMapping("/file/video")
public class FileVideoController extends FileBaseController {

    @Resource(name = "fileVideoService")
    private FileService<FileVideoEntity> fileService;

    @ApiOperation(value = "新增：保存文件到Win", notes = "保存文件到Win")
    @PostMapping("/add")
    @TokenCheck
    public ResultData<ArrayList> add(
            @ApiParam(value = "应用AppId", type = "header", required = true) @RequestHeader(value = "appId") String appId,
            @ApiParam(value = "验证Token", type = "header", required = true) @RequestHeader(value = "token") String token,
            @ApiParam(value = "业务类型", required = true) @RequestParam("bizType") String bizType,
            @ApiParam(value = "文件对象列表", required = true) @RequestParam("files") MultipartFile[] fileList) {
        //http://192.168.124.12:10002/file/add

        setService(fileService);
        return addFile(appId, token, bizType, "videos", fileList);
    }

    @ApiOperation(value = "新增：保存文件到Linux", notes = "保存文件到Linux")
    @PostMapping("/addByLinux")
    @TokenCheck
    public ResultData addByLinux(
            @ApiParam(value = "应用AppId", type = "header", required = true) @RequestHeader(value = "appId") String appId,
            @ApiParam(value = "验证Token", type = "header", required = true) @RequestHeader(value = "token") String token,
            @ApiParam(value = "业务类型", required = true) @RequestParam("bizType") String bizType,
            @ApiParam(value = "文件对象列表", required = true) @RequestParam("files") MultipartFile[] fileList) {
        //http://192.168.124.12:10002/file/addByLinux
        setService(fileService);
        return addFileByLinux(appId, token, bizType, "videos",  fileList);
    }

    @ApiOperation(value = "删除：根据Id删除文件", notes = "删除文件")
    @DeleteMapping("/deleteById/{id}")
    @TokenCheck
    public ResultData<String> deleteById(
            @ApiParam(value = "验证Token", type = "header", required = true) @RequestHeader(value = "token") String token
            , @PathVariable String id) {
        setService(fileService);
        return delById(id);
    }

    @ApiOperation(value = "删除：根据bizId删除文件", notes = "根据bizId删除文件")
    @DeleteMapping("/delByBizId")
    @TokenCheck
    public ResultData<String> deleteByBizId(
            @ApiParam(value = "验证Token", type = "header", required = true) @RequestHeader(value = "token") String token
            , @RequestParam("bizId") String bizId) {
        setService(fileService);
        return delByBizId(bizId);
    }

    /**
     * 查询单一业务所有文件数据
     *
     * @param token t
     * @param bizId b
     * @return
     */
    @ApiOperation(value = "查找：单一业务所有文件", notes = "查找：单一业务所有文件")
    @GetMapping("/findFileByBizId")
    @TokenCheck
    public ResultData<ArrayList<FileVideoEntity>> findFileByBizId(
            @ApiParam(value = "验证Token", type = "header", required = true) @RequestHeader(value = "token") String token
            , @RequestParam("bizId") String bizId) {
        setService(fileService);
        return findFilesByBizId(bizId);
    }

    @ApiOperation(value = "查找：某用户所有文件", notes = "查找：某用户所有文件")
    @GetMapping("/findFileByOwnerName")
    @TokenCheck
    public ResultData<PageData<FileVideoEntity>> findFileByOwnerName(
            @ApiParam(value = "验证Token", type = "header", required = true) @RequestHeader(value = "token") String token,
            @RequestParam("fileType") String fileType,
            @RequestParam("currPage") int currPage,
            @RequestParam("pageSize") int pageSize) {

        setService(fileService);

        return findFileListByOwnerName(token, fileType, currPage, pageSize);
    }
}
