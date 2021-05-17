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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/user/")
public class UserController {

    @Autowired
    UserServiceImpl userService;



    @RequestMapping(value = "login.do", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ServerResponse<UserVO> login(String username, String password, HttpSession session){
        ServerResponse<UserVO> response = userService.login(username, password);
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
        session.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.createBySuccess();
    }


    @RequestMapping(value = "get_user_info.do", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ServerResponse<UserVO> getUserInfo(HttpSession session) {
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
