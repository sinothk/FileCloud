package com.sinothk.cloud.file.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sinothk.cloud.file.domain.FileBizEntity;
import org.springframework.stereotype.Repository;

@Repository("fileBizMapper")
public interface FileBizMapper extends BaseMapper<FileBizEntity> {
}
