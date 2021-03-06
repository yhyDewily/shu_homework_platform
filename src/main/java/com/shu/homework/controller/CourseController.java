package com.shu.homework.controller;

import com.shu.homework.common.Const;
import com.shu.homework.common.ServerResponse;
import com.shu.homework.entity.Course;
import com.shu.homework.service.Impl.CourseServiceImpl;
import com.shu.homework.service.Impl.UserServiceImpl;
import com.shu.homework.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping(value = "get_all.do", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ServerResponse getAll(@RequestParam(value = "pageNum", defaultValue = "1")int pageNum,
                                 @RequestParam(value = "pageSize", defaultValue = "5")int pageSize){
        return courseService.getAllCourseByPage(pageNum-1, pageSize);
    }

    @RequestMapping(value = "get_course_info.do", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ServerResponse getCourseInfo(String courseName) {
        return courseService.getCourseInfo(courseName);
    }

    @RequestMapping(value = "get_course_info_by_id.do", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ServerResponse getCourseInfoById(String courseId) {
        return courseService.getCourseInfoById(courseId);
    }

    @RequestMapping(value = "get_course_info_by_teacher.do", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ServerResponse getCourseInfoByTeacher (HttpSession session, @RequestParam(value = "pageNum", defaultValue = "1")int pageNum,
                                                  @RequestParam(value = "pageSize", defaultValue = "5")int pageSize) {
        UserVO currentUser = (UserVO) session.getAttribute(Const.CURRENT_USER);
        if(currentUser == null) return ServerResponse.createByErrorMessage("???????????????");
        return courseService.getCourseInfoByTeacher(currentUser.getId(), pageNum-1, pageSize);
    }


    @RequestMapping(value = "add_stu_course.do", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ServerResponse addStuCourse(HttpSession session, String courseId){
        UserVO currentUser = (UserVO) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null) return ServerResponse.createByErrorMessage("???????????????");
        return courseService.addStuCourse(currentUser.getId(), courseId);
    }

    @RequestMapping(value = "get_student_course.do", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ServerResponse getStuCourse(HttpSession session, @RequestParam(value = "pageNum", defaultValue = "1")int pageNum,
                                       @RequestParam(value = "pageSize", defaultValue = "5")int pageSize) {
        UserVO currentUser = (UserVO) session.getAttribute(Const.CURRENT_USER);
        if(currentUser == null) return ServerResponse.createByErrorMessage("???????????????");
        return courseService.getStuCourse(currentUser.getId(), pageNum-1, pageSize);
    }

    @RequestMapping(value = "save.do", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ServerResponse courseSave(HttpSession session, Course course) {
        UserVO currentUser = (UserVO) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null) return ServerResponse.createByErrorMessage("???????????????");
        if (currentUser.getRole() < 1) return ServerResponse.createByErrorMessage("??????????????????");
        if(courseService.checkAuth(currentUser.getId(), course.getCourseId())){
            return courseService.UpdateCourse(course);
        } else {
            return ServerResponse.createByErrorMessage("???????????????????????????????????????");
        }
    }

    @RequestMapping(value = "search_course.do", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ServerResponse searchCourse(HttpSession session, String keyword, int action, int tab, @RequestParam(value = "pageNum", defaultValue = "1")int pageNum,
                                       @RequestParam(value = "pageSize", defaultValue = "5")int pageSize) {
        UserVO currentUser = (UserVO) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null) return ServerResponse.createByErrorMessage("???????????????");
        return courseService.searchCourse(currentUser.getId(), keyword, action,tab, pageNum-1, pageSize);
    }

    @RequestMapping(value = "get_unchosen_course.do", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ServerResponse getUnchosenCourse (HttpSession session, @RequestParam(value = "pageNum", defaultValue = "1")int pageNum,
                                             @RequestParam(value = "pageSize", defaultValue = "5")int pageSize) {
        UserVO currentUser = (UserVO) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null) return ServerResponse.createByErrorMessage("???????????????");
        return courseService.getUnchosenCourse(currentUser.getId(), pageNum-1, pageSize);
    }

    @RequestMapping(value = "submit_course.do", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ServerResponse createCourse(HttpSession session, Course course) {
        UserVO currentUser = (UserVO) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null) return ServerResponse.createByErrorMessage("???????????????");
        course.setT_id(currentUser.getId());
        return courseService.createCourse(course);
    }
}
