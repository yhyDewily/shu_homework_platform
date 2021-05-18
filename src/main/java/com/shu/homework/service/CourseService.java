package com.shu.homework.service;

import com.shu.homework.common.ServerResponse;
import com.shu.homework.entity.Course;
import com.shu.homework.entity.User;

public interface CourseService {

    ServerResponse addStuCourse(Long studentId, String courseId);

    ServerResponse getStuCourse(Long studentId, int pageNum, int pageSize);

    ServerResponse UpdateCourse(Course course);
}
