package com.sinothk.cloud.file.controller;

import com.sinothk.base.entity.ResultData;
import com.sinothk.cloud.comm.authorization.TokenCheck;
import com.sinothk.cloud.file.domain.FileBizEntity;
import com.sinothk.cloud.file.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@Api(tags = "文件管理：业务文件")
@RestController
@RequestMapping("/file/biz")
public class FileBizController extends FileBaseController {

    @Resource(name = "fileBizService")
    private FileService<FileBizEntity> fileService;


    @ApiOperation(value = "新增：保存文件到Win", notes = "保存文件到Win")
    @PostMapping("/add")
    @TokenCheck
    public ResultData add(
            @ApiParam(value = "应用AppId", type = "header", required = true) @RequestHeader(value = "appId") String appId,
            @ApiParam(value = "验证Token", type = "header", required = true) @RequestHeader(value = "token") String token,
            @ApiParam(value = "业务类型", required = true) @RequestParam("bizType") String bizType,
            @ApiParam(value = "文件类型", required = true) @RequestParam("fileType") String fileType,
            @ApiParam(value = "文件对象列表", required = true) @RequestParam("files") MultipartFile[] fileList) {

        //http://192.168.124.12:10002/file/add
        setService(fileService);
        return addFile(appId, token, bizType, fileType, fileList);
    }

    @ApiOperation(value = "新增：保存文件到Linux", notes = "保存文件到Linux")
    @PostMapping("/addByLinux")
    @TokenCheck
    public ResultData addByLinux(
            @ApiParam(value = "应用AppId", type = "header", required = true) @RequestHeader(value = "appId") String appId,
            @ApiParam(value = "验证Token", type = "header", required = true) @RequestHeader(value = "token") String token,
            @ApiParam(value = "业务类型", required = true) @RequestParam("bizType") String bizType,
            @ApiParam(value = "文件类型", required = true) @RequestParam("fileType") String fileType,
            @ApiParam(value = "文件对象列表", required = true) @RequestParam("files") MultipartFile[] fileList) {
        //http://192.168.124.12:10002/file/addByLinux
        setService(fileService);
        return addFileByLinux(appId, token, bizType, fileType, fileList);
    }

    @ApiOperation(value = "删除：根据Id删除文件", notes = "删除文件")
    @DeleteMapping("/deleteById/{id}")
    @TokenCheck
    public ResultData deleteById(
            @ApiParam(value = "验证Token", type = "header", required = true) @RequestHeader(value = "token") String token
            , @PathVariable String id) {
        setService(fileService);
        return delById(id);
    }

    @ApiOperation(value = "删除：根据bizId删除文件", notes = "根据bizId删除文件")
    @DeleteMapping("/delByBizId")
    @TokenCheck
    public ResultData deleteByBizId(
            @ApiParam(value = "验证Token", type = "header", required = true) @RequestHeader(value = "token") String token
            , @RequestParam("bizId") String bizId) {
        setService(fileService);
        return delByBizId(bizId);
    }

    /**
     * 查询单一业务所有文件数据
     *
     * @param token token
     * @param bizId bizId
     * @return 结果
     */
    @ApiOperation(value = "查找：单一业务所有文件", notes = "查找：单一业务所有文件")
    @GetMapping("/findFileByBizId")
    @TokenCheck
    public ResultData findFileByBizId(
            @ApiParam(value = "验证Token", type = "header", required = true) @RequestHeader(value = "token") String token
            , @RequestParam("bizId") String bizId) {
        setService(fileService);
        return findFilesByBizId(bizId);
    }

    @ApiOperation(value = "查找：某用户所有文件", notes = "查找：某用户所有文件")
    @GetMapping("/findFileByOwnerName")
    @TokenCheck
    public ResultData findFileByOwnerName(
            @ApiParam(value = "验证Token", type = "header", required = true) @RequestHeader(value = "token") String token,
            @RequestParam("currPage") int currPage,
            @RequestParam("pageSize") int pageSize) {

        setService(fileService);

        return findFileListByOwnerName(token, currPage, pageSize);
    }
}
