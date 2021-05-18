package com.shu.homework.controller;

import com.shu.homework.common.ServerResponse;
import com.shu.homework.entity.Group;
import com.shu.homework.service.Impl.GroupServiceImpl;
import com.shu.homework.service.Impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public ServerResponse<Group> createGroup(String name, String captain, List<String> member, String courseId) {
        return null;
    }


}
