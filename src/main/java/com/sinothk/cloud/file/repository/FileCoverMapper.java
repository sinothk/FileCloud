package com.sinothk.cloud.file.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sinothk.cloud.file.domain.FileCoverEntity;
import org.springframework.stereotype.Repository;

@Repository("fileCoverMapper")
public interface FileCoverMapper extends BaseMapper<FileCoverEntity> {
}
