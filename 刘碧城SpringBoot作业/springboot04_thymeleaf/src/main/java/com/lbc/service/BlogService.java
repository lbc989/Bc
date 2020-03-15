package com.lbc.service;

import com.lbc.pojo.Blog;
import org.springframework.data.domain.Page;
import java.util.List;

public interface BlogService  {
    public List<Blog> findAll();
    Page<Blog> getBlogList(int pageNum, int pageSize);
}
