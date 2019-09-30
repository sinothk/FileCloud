package com.sinothk.cloud.file.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.ToString;

@ApiModel(description = "用户信息")
@Data
@ToString
@TableName(value = "tb_file_system")
public class FileSystemEntity extends FileBaseEntity{
}
