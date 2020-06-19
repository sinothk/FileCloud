package com.sinothk.cloud.file.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@ApiModel(description = "用户信息")
@Data
@ToString
@TableName(value = "tb_file_video")
public class FileVideoEntity extends FileBaseEntity {

    @ApiModelProperty(value = "视频封面")
    @TableField("file_cover")
    private String fileCover;
}
