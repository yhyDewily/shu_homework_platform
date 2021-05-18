package com.shu.homework.service.Impl;

import com.shu.homework.common.ServerResponse;
import com.shu.homework.entity.Course;
import com.shu.homework.entity.SCourse;
import com.shu.homework.respository.CourseRepository;
import com.shu.homework.respository.SCourseRepository;
import com.shu.homework.respository.UserRepository;
import com.shu.homework.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SCourseRepository sCourseRepository;

    @Override
    public ServerResponse addStuCourse(Long studentId, String courseId) {
        if(courseRepository.checkCourseId(courseId) < 1)
            return ServerResponse.createByErrorMessage("课程不存在");
        if(sCourseRepository.findByCourseIdAndStudentId(courseId, studentId)!=null){
            return ServerResponse.createByErrorMessage("该课程已选");
        }
        SCourse sCourse = new SCourse();
        sCourse.setCourseId(courseId);
        sCourse.setStudentId(studentId);
        sCourse.setStatus("no");
        try {
            sCourseRepository.save(sCourse);
        } catch (Exception e) {
            return ServerResponse.createByErrorMessage("课程添加失败");
        }

        return ServerResponse.createBySuccessMessage("课程添加成功");
    }

    @Override
    public ServerResponse getStuCourse(Long studentId, int pageNum, int pageSize) {
        //分页显示学生课程
        Pageable pageable = PageRequest.of(pageNum,pageSize);
        Page<SCourse> sCoursePage = sCourseRepository.findByStudentId(studentId, pageable);
        List<SCourse> sCourseList = sCoursePage.getContent();
        List<Course> courseList = new ArrayList<>();
        for(SCourse sCourse : sCourseList) {
            Course course = courseRepository.findByCourseId(sCourse.getCourseId());
            courseList.add(course);
        }
        //获取学生所有课程后返回
        Page<Course> pageCourse = new PageImpl<Course>(courseList,pageable,sCoursePage.getTotalElements());
        return ServerResponse.createBySuccess(pageCourse);
    }


}
