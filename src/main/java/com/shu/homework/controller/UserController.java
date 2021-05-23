package com.shu.homework.controller;

import com.shu.homework.common.Const;
import com.shu.homework.common.ResponseCode;
import com.shu.homework.common.ServerResponse;
import com.shu.homework.entity.User;
import com.shu.homework.service.Impl.UserServiceImpl;
import com.shu.homework.service.UserService;
import com.shu.homework.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/user/")
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @RequestMapping(value = "login.do", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ServerResponse<UserVO> login(String email, String password, HttpServletRequest servlet){
        ServerResponse<UserVO> response = userService.login(email, password);
        HttpSession session = servlet.getSession();
        if(response.isSuccess()) {
            session.setAttribute(Const.CURRENT_USER, response.getData());
        }
        return response;
    }

    @RequestMapping(value = "register.do", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ServerResponse<String> register(User user) {
        return userService.register(user);
    }

    @RequestMapping(value = "logout.do", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ServerResponse<String> logout(HttpSession session) {
        System.out.println(session.getId());
        session.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.createBySuccess();
    }


    @RequestMapping(value = "get_user_info.do", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ServerResponse<UserVO> getUserInfo(HttpServletRequest servlet) {
        HttpSession session = servlet.getSession();
        UserVO currentUserVO = (UserVO) session.getAttribute(Const.CURRENT_USER);
        if (currentUserVO == null) {
           return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "未登录，需要强制登录");
        }
        return userService.getInformation(currentUserVO.getId());
    }

    @RequestMapping(value = "get_user_by_mail.do", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ServerResponse<UserVO> getUserByMail(String mail) {
        return userService.getByMail(mail);
    }

    @RequestMapping(value = "get_all_student.do", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ServerResponse<List<UserVO>>getAllStudents(HttpSession session) {
        UserVO currentUser = (UserVO)session.getAttribute(Const.CURRENT_USER);
        if(currentUser == null) return ServerResponse.createByErrorMessage("用户未登录");
        if(currentUser.getRole() < 1) return ServerResponse.createByErrorMessage("权限不够");
        return userService.getAllStudents();
    }


    @RequestMapping(value = "update_user_info.do", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ServerResponse<UserVO> updateInformation(HttpSession session, User user) {
        UserVO currentUser = (UserVO)session.getAttribute(Const.CURRENT_USER);
        if(currentUser == null) return ServerResponse.createByErrorMessage("用户未登录");
        user.setId(currentUser.getId());
        ServerResponse<UserVO> response = userService.updateInformation(user);
        if(response.isSuccess()) session.setAttribute(Const.CURRENT_USER, response.getData());
        return response;
    }

    @RequestMapping(value = "update_password.do", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ServerResponse<String> updatePassword(HttpSession session, String oldPassword, String newPassword) {
        UserVO currentUser = (UserVO)session.getAttribute(Const.CURRENT_USER);
        if(currentUser == null) return ServerResponse.createByErrorMessage("用户未登录");
        return userService.updatePassword(currentUser.getId(), newPassword, oldPassword);
    }

}
