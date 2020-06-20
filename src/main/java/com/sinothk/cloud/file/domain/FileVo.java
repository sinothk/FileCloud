package com.sinothk.cloud.file.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@ApiModel(description = "文件信息")
@Data
@ToString
public class FileVo {

    @ApiModelProperty(value = "拥有者account")
    private String ownerAccount;

    /**
     * fileType：Img，Video "文件类型"
     */
    @ApiModelProperty(value = "文件类型: Img、Video等")
    private String fileType;

}



