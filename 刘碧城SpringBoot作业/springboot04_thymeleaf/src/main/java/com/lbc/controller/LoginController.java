package com.lbc.controller;

import com.lbc.pojo.Blog;
import com.lbc.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

@Controller
public class LoginController {
    @Autowired
    BlogService blogService;
    @RequestMapping("/")
    public String index(Model model){
        return "redirect:/list?pageNum=0";
    }
    @RequestMapping("/toLoginPage")
    public String toLoginPage(Model model){
        model.addAttribute("currentYear", Calendar.getInstance().get(Calendar.YEAR));
        return "login";
    }

    @RequestMapping("/list")
    public String list(Model model, @RequestParam(value = "pageNum", defaultValue = "0") int pageNum, @RequestParam(value = "pageSize", defaultValue = "1") int pageSize) {
        Page<Blog> blogs=blogService.getBlogList(pageNum, pageSize);
        model.addAttribute("blogs", blogs);
        return "index";
    }

}
