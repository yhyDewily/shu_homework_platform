package com.shu.homework.service.Impl;

import com.shu.homework.common.ServerResponse;
import com.shu.homework.entity.Group;
import com.shu.homework.entity.SCourse;
import com.shu.homework.entity.User;
import com.shu.homework.respository.GroupRepository;
import com.shu.homework.respository.SCourseRepository;
import com.shu.homework.respository.UserRepository;
import com.shu.homework.service.GroupService;
import com.shu.homework.vo.GroupVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SCourseRepository sCourseRepository;

    @Override
    public ServerResponse<Group> createGroup(GroupVO group) {
        if(checkMemberExist(group.getMember())) return ServerResponse.createByErrorMessage("小组成员中有成员已加入其它组，请确认后再建立小组");
        Group groupNew = new Group();
        groupNew.setSize(group.getMember().size());
        groupNew.setName(group.getName());
        groupNew.setCaptain(group.getCaptain());
        groupNew.setCourseId(group.getCourseId());
        //将小组成员拆分存入数据库
        StringBuilder memberNew = new StringBuilder();
        for(String member : group.getMember()) {
            memberNew.append(";").append(member);
        }
        groupNew.setMember(memberNew.toString());
        try {
            groupRepository.save(groupNew);
        } catch (Exception e) {
            return ServerResponse.createByErrorMessage("小组创建失败");
        }
        return ServerResponse.createBySuccess("小组创建成功", groupNew);
    }

    @Override
    public ServerResponse addMember(String studentName, Long groupId, String courseId) {
        if(userRepository.findByName(studentName) == null) return ServerResponse.createByErrorMessage("学生不存在");
        if(groupRepository.getById(groupId) == null) return ServerResponse.createByErrorMessage("小组不存在");
        User student = userRepository.findByName(studentName);
        if(!checkIsClassMember(student.getId(), courseId)) return ServerResponse.createByErrorMessage("该成员非该课程成员");
        //添加小组成员
        Group group = groupRepository.getById(groupId);
        String groupMember = group.getMember();
        groupMember = groupMember + ";" + studentName;
        group.setMember(groupMember);
        try {
            groupRepository.save(group);
        } catch (Exception e) {
            return ServerResponse.createByErrorMessage("成员添加失败");
        }
        return ServerResponse.createBySuccess("添加成功", group);
    }

    @Override
    public ServerResponse getGroupInfo(Long studentId, String courseId) {
        User user = userRepository.findByUserId(studentId);
        Group group = groupRepository.getGroupByMemberAndCourseId(user.getName(), courseId);
        return null;
    }


//    @Override
//    public ServerResponse stuGetGroupInfo(Long studentId, Long groupId) {
//        Group group = groupRepository.getById(groupId);
//        if(group == null) return ServerResponse.createByErrorMessage()
//    }

    //检查小组成员中是否有人已加入其它小组
    public boolean checkMemberExist(List<String> member) {
        for (String s : member) {
            if (groupRepository.checkGroupMember(s) > 0) return true;
        }
        return false;
    }

    public boolean checkIsClassMember(Long memberId, String courseId) {
        SCourse sCourse = sCourseRepository.findByCourseIdAndStudentId(courseId, memberId);
        return sCourse !=null;
    }
}