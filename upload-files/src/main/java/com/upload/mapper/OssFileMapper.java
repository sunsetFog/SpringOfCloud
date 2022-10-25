package com.upload.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.upload.entity.OssFile;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Description: oss云存储Mapper
 * @author: jeecg-boot
 */
@Mapper // No qualifying bean of type 'com.upload.mapper.OssFileMapper' available: expected at least 1 bean which qualifies as autowire candidate.
@Repository // Error creating bean with name 'ossFileController': Unsatisfied dependency expressed through field 'ossFileService';
public interface OssFileMapper extends BaseMapper<OssFile> {

}
