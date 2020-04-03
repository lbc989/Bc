package com.lbc.service;

import com.lbc.pojo.Resume;
import com.lbc.pojo.ResumeDTO;

import java.util.List;

public interface ResumeService {
    //查询一条记录
    ResumeDTO findOne(Long id);
     List<Resume> findAll();
    //添加记录
    ResumeDTO add(ResumeDTO resumeDTO);

    //删除记录
    void delete(Long id);

    //更新记录
    void update(ResumeDTO resumeDTO);
}
