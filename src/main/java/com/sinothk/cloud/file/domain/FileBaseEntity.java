package com.sinothk.cloud.file.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class FileBaseEntity {

    @ApiModelProperty(value = "ID")
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    @ApiModelProperty(value = "AppID")
    @TableField("app_id")
    private String appId;

    @ApiModelProperty(value = "业务编码")
    @TableField("biz_id")
    private String bizId;

    @ApiModelProperty(value = "业务类型")
    @TableField("biz_type")
    private String bizType;

    @ApiModelProperty(value = "上传用户")
    @TableField("owner_user")
    private String ownerUser;

    /**
     * fileType：Img，Video
     */
    @ApiModelProperty(value = "文件类型")
    @TableField("file_type")
    private String fileType;

    @ApiModelProperty(value = "文件名称")
    @TableField("file_name")
    private String fileName;

    @ApiModelProperty(value = "文件url")
    @TableField("file_url")
    private String fileUrl;

    @ApiModelProperty(value = "文件大小")
    @TableField("file_size")
    private long fileSize;

    @ApiModelProperty(value = "新增时间")
    @TableField("create_time")
    private Date createTime;

    @ApiModelProperty(value = "到期时间")
    @TableField("expired_time")
    private Date expiredTime;
}
