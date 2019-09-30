package com.sinothk.cloud.file.domain;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class FileVo {

   // "上传用户"
    private String ownerUser;

    /**
     * fileType：Img，Video "文件类型"
     */
    private String fileType;

}



