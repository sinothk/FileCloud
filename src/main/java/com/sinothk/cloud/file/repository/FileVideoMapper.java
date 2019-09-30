package com.sinothk.cloud.file.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sinothk.cloud.file.domain.FileVideoEntity;
import org.springframework.stereotype.Repository;

@Repository("fileVideoMapper")
public interface FileVideoMapper extends BaseMapper<FileVideoEntity> {
}
