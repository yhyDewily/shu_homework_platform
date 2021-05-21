package com.shu.homework.service;

import com.shu.homework.common.ServerResponse;
import com.shu.homework.entity.Course;
import com.shu.homework.entity.User;

public interface CourseService {

    ServerResponse addStuCourse(Long studentId, String courseId);

    ServerResponse getStuCourse(Long studentId, int pageNum, int pageSize);

    ServerResponse UpdateCourse(Course course);

    ServerResponse getAllCourseByPage(int pageNum, int pageSize);

    ServerResponse searchCourse(Long userId, String keyword, int action, int tab, int pageNum, int pageSize);

    ServerResponse getCourseInfo(String courseName);

    ServerResponse getCourseInfoById(String courseId);

    ServerResponse getUnchosenCourse(Long studentId, int pageNum, int pageSize);
}
