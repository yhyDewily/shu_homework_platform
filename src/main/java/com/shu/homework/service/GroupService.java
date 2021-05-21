package com.shu.homework.service;

import com.shu.homework.common.ServerResponse;
import com.shu.homework.entity.Group;
import com.shu.homework.vo.GroupVO;

public interface GroupService {

    ServerResponse<Group> createGroup(GroupVO group);

    ServerResponse addMember(String studentName, Long groupId, String courseId);

    ServerResponse getGroupInfo(Long studentId, String courseId);


    //ServerResponse stuGetGroupInfo(Long studentId, Long groupId);
}
