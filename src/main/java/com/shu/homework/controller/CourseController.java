package com.shu.homework.controller;

import com.shu.homework.common.Const;
import com.shu.homework.common.ServerResponse;
import com.shu.homework.entity.User;
import com.shu.homework.service.Impl.CourseServiceImpl;
import com.shu.homework.service.Impl.UserServiceImpl;
import com.shu.homework.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/course/")
public class CourseController {

    @Autowired
    CourseServiceImpl courseService;

    @Autowired
    UserServiceImpl userService;

    @RequestMapping(value = "addStuCourse.do", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ServerResponse addStuCourse(HttpSession session, User user, String courseId){
        UserVO currentUser = (UserVO) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null) return ServerResponse.createByErrorMessage("用户未登录");
        if (!currentUser.getId().equals(user.getId()) && currentUser.getRole() < 1) return ServerResponse.createByErrorMessage("不可替其他同学添加课程");
        return courseService.addStuCourse(user.getId(), courseId);
    }

    @RequestMapping(value = "get_student_course", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ServerResponse getStuCourse(HttpSession session, @RequestParam(value = "pageNum", defaultValue = "1")int pageNum,
                                       @RequestParam(value = "pageSize", defaultValue = "15")int pageSize) {
        UserVO currentUser = (UserVO) session.getAttribute(Const.CURRENT_USER);
        if(currentUser == null) return ServerResponse.createByErrorMessage("用户未登录");
        return courseService.getStuCourse(currentUser.getId(), pageNum-1, pageSize);
    }

}
