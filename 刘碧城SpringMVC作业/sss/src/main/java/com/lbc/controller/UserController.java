package com.lbc.controller;

import com.lbc.dao.ResumeDao;
import com.lbc.pojo.Resume;
import com.lbc.pojo.ResumeDTO;
import com.lbc.pojo.User;
import com.lbc.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UserController {

    // 要测试IOC哪个对象注入即可
    @Autowired
    private ResumeService resumeService;
    /**
     * 向用户登录页面跳转
     */
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String toLogin(){
        return  "login";
    }

    /**
     * 用户登录
     * @param user
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(User user, Model model, HttpSession session){
        //获取用户名和密码
        String username=user.getUsername();
        String password=user.getPassword();
        //些处横板从数据库中获取对用户名和密码后进行判断
        if(username!=null&&username.equals("admin")&&password!=null&&password.equals("admin")){
            //将用户对象添加到Session中
            session.setAttribute("USER_SESSION",user);
            //重定向到主页面的跳转方法
            return "redirect:main";
        }else {
            model.addAttribute("msg","用户名或密码错误，请重新登录！");
            return "login";
        }

    }

    @RequestMapping(value = "/main")
    public ModelAndView testFindAll(){
        List<Resume> all = resumeService.findAll();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("resume",all);
        modelAndView.setViewName("main");
        return modelAndView;

    }
    // public String toMain(){
    //     return "main";
    // }

    @RequestMapping(value = "/add")
    public ModelAndView addView() {
        return new ModelAndView("/add");
    }

    @RequestMapping(value = "/addResume",method = RequestMethod.POST)
    public String addSubmit(ResumeDTO resumeDTO) {
        resumeService.add(resumeDTO);
        return "redirect:/main";
    }
    @RequestMapping(value = "/edit/{id}")
    public ModelAndView editView(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView();
        ResumeDTO resumeDTO = resumeService.findOne(id);
        modelAndView.addObject("resumeDTO",resumeDTO);
        modelAndView.setViewName("/edit");
        return modelAndView;
    }

    @RequestMapping(value = "/editUser",method = RequestMethod.POST)
    public String editSubmit(ResumeDTO resumeDTO) {
        resumeService.update(resumeDTO);
        return "redirect:/main";
    }
    @RequestMapping(value = "/delete")
    public String delete(@Param("id")Long id) {
        resumeService.delete(id);
        return "redirect:/main";
    }
    @RequestMapping(value = "/logout")
    public String logout(HttpSession session){
        //清除session
        session.invalidate();
        //重定向到登录页面的跳转方法
        return "redirect:login";
    }

}
