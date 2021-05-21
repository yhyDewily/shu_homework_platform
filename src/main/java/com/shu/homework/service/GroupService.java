package com.shu.homework.service;

import com.shu.homework.common.ServerResponse;
import com.shu.homework.entity.Group;
import com.shu.homework.vo.GroupVO;

public interface GroupService {

    ServerResponse<GroupVO> createGroup(Long studentId, String courseId);

    ServerResponse<GroupVO> createGroupByTeacher(GroupVO group);

    ServerResponse addMember(String studentName, Long groupId, String courseId);

    ServerResponse getGroupInfo(Long studentId, String courseId);

    ServerResponse expire_group(Long studentId, String courseId);

    ServerResponse exit_group(Long studentId, String courseId);

    ServerResponse get_all_group(String courseId);

    ServerResponse join_group(Long studentId, Long groupId);

    ServerResponse assignCaptain(String name, String courseId, String studentName);

    ServerResponse delMember(String courseId, String studentName);


    //ServerResponse stuGetGroupInfo(Long studentId, Long groupId);
}
