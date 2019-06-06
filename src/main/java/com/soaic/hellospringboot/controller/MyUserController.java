package com.soaic.hellospringboot.controller;

import com.github.pagehelper.PageHelper;
import com.soaic.hellospringboot.common.PageBean;
import com.soaic.hellospringboot.common.PageModel;
import com.soaic.hellospringboot.common.ResponseResult;
import com.soaic.hellospringboot.entity.MyUser;
import com.soaic.hellospringboot.services.MyUserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/user")
public class MyUserController {

    @Autowired
    private MyUserServices myUserServices;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(HttpServletRequest request) {
        try {
            String userName = request.getParameter("username");
            String password = request.getParameter("password");

            MyUser user = new MyUser();
            user.setUserName(userName);
            user.setPassword(password);
            return myUserServices.insertUser(user)+"";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @RequestMapping("/findAllUser")
    public ResponseResult<PageBean> findAllUser(PageModel pageModel) {
        ResponseResult<PageBean> responseResult;
        try {
            PageHelper.startPage(pageModel.getPageNum(), pageModel.getPageSize());
            PageHelper.orderBy("id asc");
            List<MyUser> myUsers = myUserServices.selectUser();
            PageBean<MyUser> pageInfo = new PageBean<>(myUsers);
            responseResult = new ResponseResult<>(200, "success", pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            responseResult = new ResponseResult<>(501, "failure", null);
        }
        return responseResult;
    }
}