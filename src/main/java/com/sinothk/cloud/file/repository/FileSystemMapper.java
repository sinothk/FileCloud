package com.sinothk.cloud.file.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sinothk.cloud.file.domain.FileSystemEntity;
import org.springframework.stereotype.Repository;

@Repository("fileSystemMapper")
public interface FileSystemMapper extends BaseMapper<FileSystemEntity> {
}
