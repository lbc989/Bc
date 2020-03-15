package com.lbc.service.impl;

import com.lbc.pojo.Blog;
import com.lbc.repository.BlogRepository;
import com.lbc.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    BlogRepository blogRepository;


    @Override
    public List<Blog> findAll() {
        List<Blog> all = blogRepository.findAll();
        return all;
    }

    @Override
    public Page<Blog> getBlogList(int pageNum, int pageSize) {
        Sort.Order order= new Sort.Order(Sort.Direction.ASC, "id");
        PageRequest request = PageRequest.of(pageNum,pageSize,Sort.by(order));
        Page<Blog> users = blogRepository.findAll(request);

        return users;
    }
}
