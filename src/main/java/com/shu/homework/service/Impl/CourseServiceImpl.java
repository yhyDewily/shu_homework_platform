package com.shu.homework.service.Impl;

import com.shu.homework.common.ServerResponse;
import com.shu.homework.entity.Course;
import com.shu.homework.entity.CourseGrade;
import com.shu.homework.entity.SCourse;
import com.shu.homework.entity.User;
import com.shu.homework.respository.CourseGradeRepository;
import com.shu.homework.respository.CourseRepository;
import com.shu.homework.respository.SCourseRepository;
import com.shu.homework.respository.UserRepository;
import com.shu.homework.service.CourseService;
import com.shu.homework.vo.CourseVO;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SCourseRepository sCourseRepository;

    @Autowired
    CourseGradeRepository gradeRepository;

    @Override
    public ServerResponse addStuCourse(Long studentId, String courseId) {
        if(courseRepository.checkCourseId(courseId) < 1)
            return ServerResponse.createByErrorMessage("课程不存在");
        if(sCourseRepository.findByCourseIdAndStudentId(courseId, studentId)!=null){
            return ServerResponse.createByErrorMessage("该课程已选");
        }
        SCourse sCourse = new SCourse();
        CourseGrade grade = new CourseGrade();
        grade.setCourseId(courseId);
        grade.setStudentId(studentId.toString());
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
        List<CourseVO> courseList = new ArrayList<>();
        for(SCourse sCourse : sCourseList) {
            CourseVO courseVO = new CourseVO();
            //查找课程
            Course course = courseRepository.findByCourseId(sCourse.getCourseId());
            courseVO = setCourseVO(course);
            courseList.add(courseVO);
        }
        //获取学生所有课程后返回
        Page<CourseVO> pageCourse = new PageImpl<CourseVO>(courseList,pageable,sCoursePage.getTotalElements());
        return ServerResponse.createBySuccess(pageCourse);
    }

    @Override
    public ServerResponse UpdateCourse(Course new_course) {
        Course course = courseRepository.findByCourseId(new_course.getCourseId());
        if(course == null) {
            return ServerResponse.createByErrorMessage("课程不存在");
        }
        course.setStopTime(new_course.getStopTime());
        course.setIntro(new_course.getIntro());
        course.setMajor(new_course.getMajor());
        course.setCourseName(new_course.getCourseName());
        try {
            courseRepository.save(course);
        } catch (Exception e) {
            return ServerResponse.createByErrorMessage("课程信息修改失败");
        }
        return ServerResponse.createBySuccessMessage("课程修改成功");
    }

    @Override
    public ServerResponse getAllCourseByPage(int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        Page<Course> courses = courseRepository.findAllByPage(pageable);
        List<Course> coursesContent = courses.getContent();
        List<CourseVO> courseList = new ArrayList<>();
        for(Course course_it : coursesContent) {
            CourseVO courseVO = setCourseVO(course_it);
            courseList.add(courseVO);
        }
        //获取学生所有课程后返回
        Page<CourseVO> pageCourse = new PageImpl<CourseVO>(courseList,pageable,courses.getTotalElements());
        return ServerResponse.createBySuccess(pageCourse);
    }

    @Override
    public ServerResponse searchCourse(Long userId, String keyword,int action,int tab, int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        Page<Course> courses;
        if(tab == 0) {
            if (action == 1) {
                courses = courseRepository.searchSelectByCourseName(userId, keyword, pageable);
            } else {
                courses = courseRepository.searchSelectByCourseId(userId, keyword, pageable);
            }

        } else {
            if (action == 1) {
                courses = courseRepository.searchUnSelectByCourseName(userId, keyword, pageable);
            } else {
                courses = courseRepository.searchUnselectByCourseId(userId, keyword, pageable);
            }
        }
        List<Course> coursesContent = courses.getContent();
        List<CourseVO> courseList = new ArrayList<>();
        for(Course course_it : coursesContent) {
            CourseVO courseVO = setCourseVO(course_it);
            courseList.add(courseVO);
        }
        //获取学生所有课程后返回
        Page<CourseVO> pageCourse = new PageImpl<CourseVO>(courseList,pageable,courses.getTotalElements());
        return ServerResponse.createBySuccess(pageCourse);

    }

    @Override
    public ServerResponse getCourseInfo(String courseName) {
        Course course = courseRepository.findByCourseName(courseName);
        if(course == null) return ServerResponse.createByErrorMessage("课程不存在");
        return ServerResponse.createBySuccess(course);
    }

    @Override
    public ServerResponse getCourseInfoById(String courseId) {
        Course course = courseRepository.findByCourseId(courseId);
        if(course == null) return ServerResponse.createByErrorMessage("课程不存在");
        String teacher = userRepository.findByUserId(course.getT_id()).getName();
        CourseVO courseVO = new CourseVO();
        courseVO.setTeacherId(course.getT_id().toString());
        courseVO.setCourseId(courseId);
        courseVO.setCourseName(course.getCourseName());
        courseVO.setMajor(course.getMajor());
        courseVO.setStopTime(course.getStopTime());
        courseVO.setTeacher(teacher);
        courseVO.setIntro(course.getIntro());
        return ServerResponse.createBySuccess(courseVO);
    }

    @Override
    public ServerResponse getUnchosenCourse(Long studentId, int pageNum, int pageSize) {
        User user = userRepository.findByUserId(studentId);
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        Page<Course> courses = courseRepository.findUnselectCourseById(studentId, pageable);
        List<Course> coursesContent = courses.getContent();
        List<CourseVO> courseList = new ArrayList<>();
        for(Course course_it : coursesContent) {
            CourseVO courseVO = setCourseVO(course_it);
            courseList.add(courseVO);
        }
        //获取学生所有课程后返回
        Page<CourseVO> pageCourse = new PageImpl<CourseVO>(courseList,pageable,courses.getTotalElements());
        return ServerResponse.createBySuccess(pageCourse);
    }

    @Override
    public ServerResponse getCourseInfoByTeacher(Long userId, int pageNum, int pageSize) {
        User user = userRepository.findByUserId(userId);
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        Page<Course> courses = courseRepository.findAllByTId(userId, pageable);
        List<Course> coursesContent = courses.getContent();
        List<CourseVO> courseList = new ArrayList<>();
        for(Course course_it : coursesContent) {
            CourseVO courseVO = setCourseVO(course_it);
            courseList.add(courseVO);
        }
        //获取学生所有课程后返回
        Page<CourseVO> pageCourse = new PageImpl<CourseVO>(courseList,pageable,courses.getTotalElements());
        return ServerResponse.createBySuccess(pageCourse);
    }

    @Override
    public ServerResponse createCourse(Course course) {
        //新建课程号
        String courseId = "0" + RandomStringUtils.randomNumeric(7);
        List<String> courseList = courseRepository.findAllCourseId();
        Set<String> courses = new HashSet<>(courseList);
        while (courses.contains(courseId)) {
            courseId = "0" + RandomStringUtils.randomNumeric(7);
        }
        course.setCourseId(courseId);
        try {
            courseRepository.save(course);
        } catch (Exception e) {
            return ServerResponse.createByErrorMessage("新建课程失败");
        }
        return ServerResponse.createBySuccess("创建课程成功");
    }

    public boolean checkAuth(Long teacherId, String courseId){
        Course course = courseRepository.findByCourseId(courseId);
        return teacherId.equals(course.getT_id());
    }

    public CourseVO setCourseVO (Course course) {
        CourseVO courseVO = new CourseVO();
        //查找课程
        courseVO.setCourseId(course.getCourseId());
        courseVO.setCourseName(course.getCourseName());
        //获取老师姓名
        String teacherName = userRepository.findByUserId(course.getT_id()).getName();
        courseVO.setTeacher(teacherName);
        courseVO.setMajor(course.getMajor());
        courseVO.setStopTime(course.getStopTime());
        courseVO.setIntro(course.getIntro());
        return courseVO;
    }


}
