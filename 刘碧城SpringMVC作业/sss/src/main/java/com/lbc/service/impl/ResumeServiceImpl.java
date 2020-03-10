package com.lbc.service.impl;

import com.lbc.dao.ResumeDao;
import com.lbc.pojo.Resume;
import com.lbc.pojo.ResumeDTO;
import com.lbc.pojo.User;
import com.lbc.service.ResumeService;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ResumeServiceImpl implements ResumeService {

    @Autowired
    private ResumeDao resumeDao;

    @Override
    public ResumeDTO findOne(Long id) {
        ResumeDTO resumeDTO = new ResumeDTO();
        Optional<Resume> optional =  resumeDao.findById(id);
        Resume resume = optional.get();
        BeanUtils.copyProperties(resume,resumeDTO);
        return resumeDTO;
    }

    @Override
    public List<Resume> findAll() {
        List<Resume> all = resumeDao.findAll();
        return all;

    }

    @Override
    public ResumeDTO add(ResumeDTO resumeDTO) {
        Resume resume = new Resume();
        BeanUtils.copyProperties(resumeDTO,resume);
        Resume saveResult = resumeDao.save(resume);
        return resumeDTO;
    }

    @Override
    public void delete(Long id) {
        resumeDao.deleteById(id);

    }

    @Override
    public void update(ResumeDTO resumeDTO) {
        Optional<Resume> optional = resumeDao.findById(resumeDTO.getId());
        Resume resume = optional.get();
        //用户名已存在
        //其实只会查到一条记录，因为设置了字段unique
        BeanUtils.copyProperties(resumeDTO,resume);
        Resume updateResult = resumeDao.save(resume);

    }
}
