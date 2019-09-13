package com.sinothk.cloud.file.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sinothk.cloud.file.domain.FileEntity;
import org.springframework.stereotype.Repository;

@Repository("fileMapper")
public interface FileMapper extends BaseMapper<FileEntity> {
}
