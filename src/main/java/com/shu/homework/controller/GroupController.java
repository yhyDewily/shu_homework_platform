package com.shu.homework.controller;

import com.shu.homework.common.Const;
import com.shu.homework.common.ServerResponse;
import com.shu.homework.entity.Group;
import com.shu.homework.service.Impl.GroupServiceImpl;
import com.shu.homework.service.Impl.UserServiceImpl;
import com.shu.homework.vo.GroupVO;
import com.shu.homework.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/group/")
public class GroupController {

    @Autowired
    UserServiceImpl userService;

    @Autowired
    GroupServiceImpl groupService;

    @RequestMapping(value = "create.do", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ServerResponse<Group> createGroup(HttpSession session, GroupVO group) {
        UserVO currentUser = (UserVO) session.getAttribute(Const.CURRENT_USER);
        if(currentUser == null) return ServerResponse.createByErrorMessage("用户未登录");
        return groupService.createGroup(group);
    }

    @RequestMapping(value = "add_member.do", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ServerResponse addMember(String studentName, Long groupId, String courseId) {
        return groupService.addMember(studentName, groupId, courseId);
    }

    @RequestMapping(value = "get_group_info.do", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ServerResponse getGroupInfo(HttpSession session, String courseId) {
        UserVO currentUser = (UserVO) session.getAttribute(Const.CURRENT_USER);
        if(currentUser == null) return ServerResponse.createByErrorMessage("用户未登录");
        return groupService.getGroupInfo(currentUser.getId(), courseId);
    }

//    @RequestMapping(value = "student/get_group_info.do", method = RequestMethod.POST)
//    @ResponseBody
//    @CrossOrigin
//    //学生查询小组信息
//    public ServerResponse getGroupInfoForStudent(HttpSession session, Long groupId) {
//        UserVO currentUser = (UserVO) session.getAttribute(Const.CURRENT_USER);
//        if(currentUser == null) return ServerResponse.createByErrorMessage("用户未登录");
//        return groupService.stuGetGroupInfo(currentUser.getId(), groupId);
//    }

}
