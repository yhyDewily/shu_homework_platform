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

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SCourseRepository sCourseRepository;

    @Override
    public ServerResponse<GroupVO> createGroup(Long userId, String courseId) {
        String studentName = userRepository.findByUserId(userId).getName();
        Group group = new Group();
        group.setCaptain(studentName);
        group.setSize(1);
        group.setCourseId(courseId);
        group.setMember(studentName);
        //设置小组名字
        List<Group> groupList = groupRepository.findAllByCourseId(courseId);
        if (groupList.size() == 0){
            group.setName("第1组");
        } else {
            Set<Integer> integers = new HashSet<>();
            for(Group g : groupList) {
                //正则表达式匹配
                String courseName = g.getName();
                String regEx = "[^0-9]";
                Pattern p = Pattern.compile(regEx);
                Matcher m = p.matcher(courseName);
                String num = m.replaceAll("").trim();
                Integer rank = Integer.parseInt(num);
                integers.add(rank);
            }
            for (int i=0;i<=integers.size();i++) {
                if(!integers.contains(i+1))
                    group.setName("第"+ (i+1) + "组");
            }
        }
        try {
            groupRepository.save(group);
        } catch (Exception e) {
            return ServerResponse.createByErrorMessage("创建失败");
        }
        GroupVO groupVO = setGroupVO(group);
        groupVO.setIsTeacher(0);
        if(groupVO.getCaptain().equals(studentName)) groupVO.setIsCaptain(1);
        return ServerResponse.createBySuccess("创建成功", groupVO);
    }

    @Override
    public ServerResponse<GroupVO> createGroupByTeacher(GroupVO group) {
        if(checkMemberExist(group.getMember())) return ServerResponse.createByErrorMessage("小组成员中有成员已加入其它组，请确认后再建立小组");
        Group groupNew = new Group();
        groupNew.setSize(group.getMember().size());
        groupNew.setName(group.getGroupName());
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
        return ServerResponse.createBySuccess("小组创建成功", setGroupVO(groupNew));
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
        if(group == null) return ServerResponse.createByErrorMessage("用户未加入该课程的任何小组");
        GroupVO groupVO = setGroupVO(group);
        if(user.getName().equals(groupVO.getCaptain())) groupVO.setIsCaptain(1);
        if(user.getRole() == 1) groupVO.setIsTeacher(1);
        return ServerResponse.createBySuccess(groupVO);
    }

    @Override
    public ServerResponse expire_group(Long studentId, String courseId) {
        User user = userRepository.findByUserId(studentId);
        Group group = groupRepository.getGroupByMemberAndCourseId(user.getName(), courseId);
        if(group == null) return ServerResponse.createByErrorMessage("用户未加入该课程的任何小组");
        try {
            groupRepository.delete(group);
        } catch (Exception e) {
            return ServerResponse.createByErrorMessage("解散失败");
        }
        return ServerResponse.createBySuccessMessage("成功解散");
    }

    @Override
    public ServerResponse exit_group(Long studentId, String courseId) {
        User user = userRepository.findByUserId(studentId);
        Group group = groupRepository.getGroupByMemberAndCourseId(user.getName(), courseId);
        if(group == null) return ServerResponse.createByErrorMessage("用户未加入该课程的任何小组");
        if(group.getSize() == 1){
            try {
                groupRepository.delete(group);
            } catch (Exception e) {
                return ServerResponse.createByErrorMessage("退出失败");
            }
            return ServerResponse.createBySuccessMessage("退出成功");
        }
        if (group.getCaptain().equals(user.getName())) {
            //退出者是组长，先将退出者移除，然后选取第一个组员做队长
            List<String> groupMember = new ArrayList<>(Arrays.asList(group.getMember().split(";")));
            String captain = group.getCaptain();
            groupMember.removeIf(s -> s.equals(captain));
            group.setSize(group.getSize()-1);
            group.setCaptain(groupMember.get(0));
            String memberNew = "";
            for(int i=0;i<groupMember.size();i++) {
                if (i!=groupMember.size()-1) {
                    memberNew = memberNew + groupMember.get(i) + ";";
                } else {
                    memberNew += groupMember.get(i);
                }
            }
            group.setMember(memberNew);

            try {
                groupRepository.save(group);
            } catch (Exception e) {
                return ServerResponse.createByErrorMessage("退出失败");
            }
            return ServerResponse.createBySuccessMessage("退出成功");
        }
        List<String> groupMember = new ArrayList<>(Arrays.asList(group.getMember().split(";")));
        groupMember.removeIf( s->s.equals(user.getName()));
        group.setSize(groupMember.size());
        String memberNew = "";
        for(int i=0;i<groupMember.size();i++) {
            if (i!=groupMember.size()-1) {
                memberNew = memberNew + groupMember.get(i) + ";";
            } else {
                memberNew = memberNew + groupMember.get(i);
            }
        }
        group.setMember(memberNew);

        try {
            groupRepository.save(group);
        } catch (Exception e) {
            return ServerResponse.createByErrorMessage("退出失败");
        }
        return ServerResponse.createBySuccessMessage("退出成功");
    }

    @Override
    public ServerResponse get_all_group(String courseId) {
        List<Group> groupList = groupRepository.findAllByCourseId(courseId);
        List<GroupVO> groupVOList = new ArrayList<>();
        for (Group group: groupList) {
            GroupVO groupVO = setGroupVO(group);
            groupVO.setIsTeacher(0);
            groupVO.setIsCaptain(0);
            groupVOList.add(groupVO);
        }
        return ServerResponse.createBySuccess(groupVOList);
    }

    @Override
    public ServerResponse join_group(Long studentId, Long groupId) {
        User user = userRepository.findByUserId(studentId);
        if (user == null) return ServerResponse.createByErrorMessage("用户不存在");
        Group group = groupRepository.getById(groupId);
        group.setSize(group.getSize()+1);
        List<String> groupMember = new ArrayList<>(Arrays.asList(group.getMember().split(";")));
        groupMember.add(user.getName());
        String memberNew = "";
        for(int i=0;i<group.getSize();i++) {
            if (i!=groupMember.size()-1) {
                memberNew = memberNew + groupMember.get(i) + ";";
            } else {
                memberNew = memberNew + groupMember.get(i);
            }
        }
        group.setMember(memberNew);
        try {
            groupRepository.save(group);
        } catch (Exception e) {
            return ServerResponse.createByErrorMessage("加入失败");
        }
        GroupVO groupVO = setGroupVO(group);
        groupVO.setIsTeacher(0);
        groupVO.setIsCaptain(0);
        return ServerResponse.createBySuccess(groupVO);
    }

    @Override
    public ServerResponse assignCaptain(String name, String courseId, String studentName) {
        Group group = groupRepository.getGroupByMemberAndCourseId(name, courseId);
        group.setCaptain(studentName);
        try {
            groupRepository.save(group);
        } catch (Exception e) {
            return ServerResponse.createByErrorMessage("指派组长失败");
        }
        return ServerResponse.createBySuccessMessage("指派组长成功");
    }

    @Override
    public ServerResponse delMemeber(String courseId, String studentName) {
        Group group = groupRepository.getGroupByMemberAndCourseId(studentName, courseId);
        List<String> groupMember = new ArrayList<>(Arrays.asList(group.getMember().split(";")));
        groupMember.removeIf( s->s.equals(studentName));
        group.setSize(groupMember.size());
        String memberNew = "";
        for(int i=0;i<groupMember.size();i++) {
            if (i!=groupMember.size()-1) {
                memberNew = memberNew + groupMember.get(i) + ";";
            } else {
                memberNew = memberNew + groupMember.get(i);
            }
        }
        group.setMember(memberNew);

        try {
            groupRepository.save(group);
        } catch (Exception e) {
            return ServerResponse.createByErrorMessage("删除失败");
        }
        return ServerResponse.createBySuccessMessage("删除成功");
    }

    @Override
    public ServerResponse teacher_expire_group(Long groupId) {
        Group group = groupRepository.getById(groupId);
        try {
            groupRepository.delete(group);
        } catch (Exception e) {
            return ServerResponse.createByErrorMessage("解散失败");
        }
        return ServerResponse.createBySuccessMessage("解散成功");
    }


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

//    public List<String> delMember(List<String>members, String member) {
//        members.removeIf(s -> s.equals(member));
//        return members;
//    }

    public GroupVO setGroupVO(Group group) {
        GroupVO groupVO = new GroupVO();
        List<String> groupMember = new ArrayList<>(Arrays.asList(group.getMember().split(";")));
        groupVO.setMember(groupMember);
        groupVO.setCaptain(group.getCaptain());
        groupVO.setCourseId(group.getCourseId());
        groupVO.setGroupName(group.getName());
        groupVO.setSize(group.getSize());
        groupVO.setId(group.getId());
        return groupVO;
    }
}