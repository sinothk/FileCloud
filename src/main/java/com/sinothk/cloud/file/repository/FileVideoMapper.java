package com.sinothk.cloud.file.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sinothk.cloud.file.domain.FileVideoEntity;
import com.sinothk.cloud.file.domain.FileVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository("fileVideoMapper")
public interface FileVideoMapper extends BaseMapper<FileVideoEntity> {

    @Select("SELECT a.* FROM tb_file_video AS a " +
            "RIGHT JOIN (SELECT biz_id, MAX(create_time) AS maxtime FROM tb_file_video WHERE owner_user = \"${vo.getOwnerUser()}\" AND file_type = \"${vo.getFileType()}\" AND" +
            " biz_id IS NOT NULL GROUP BY biz_id) AS b ON a.biz_id=b.biz_id AND a.create_time=b.maxtime ORDER BY a.create_time DESC")
    IPage<FileVideoEntity> selectFilePageList(IPage page, @Param("vo") FileVo vo);
}
