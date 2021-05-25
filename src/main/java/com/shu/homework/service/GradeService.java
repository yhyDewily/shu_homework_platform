package com.shu.homework.service;

import com.shu.homework.common.ServerResponse;

/**
 * 成绩管理service
 */
public interface GradeService {
    /**** 教师功能 ****/

    // 录入成绩
    public ServerResponse entryGrade(String studentId, String courseId, int usual, int exam);
    // 修改成绩
    public ServerResponse modifyGrade(String studentId, String courseId, int usual, int exam);
    // 修改课程平时成绩占比
    public ServerResponse modifUsualRatio(String courseId, int usualRatio);

    /**** 学生功能 ****/

    // 查询个人所有成绩
    public ServerResponse getGradesList(String studentId);
    // 查询个人单科成绩
    public ServerResponse getGrade(String studentId, String courseId);

    ServerResponse getAllGrade(String courseId);
}
