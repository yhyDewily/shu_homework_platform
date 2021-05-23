package com.shu.homework.service.Impl;

import com.shu.homework.common.ServerResponse;
import com.shu.homework.entity.Course;
import com.shu.homework.entity.CourseGrade;
import com.shu.homework.respository.CourseGradeRepository;
import com.shu.homework.respository.CourseRationRepository;
import com.shu.homework.respository.CourseRepository;
import com.shu.homework.service.GradeService;
import com.shu.homework.vo.StuGradeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GradeServiceImpl implements GradeService {

    @Autowired
    private CourseGradeRepository gradeRepository;

    @Autowired
    private CourseRationRepository rationRepository;

    @Autowired
    private CourseRepository courseRepository;

    /**** 教师功能 ****/
    // 录入成绩
    @Override
    public ServerResponse entryGrade(String studentId, String courseId, int usual, int exam) {

        CourseGrade grade = new CourseGrade();
        grade.setStudentId(studentId);
        grade.setCourseId(courseId);
        grade.setUsual(usual);
        grade.setExam(exam);

        try {
            gradeRepository.save(grade);
            return ServerResponse.createBySuccessMessage("成绩录入成功");
        } catch (Exception e){
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("数据库错误，录入失败");
        }

    }

    // 修改成绩
    @Override
    public ServerResponse modifyGrade(String studentId, String courseId, int usual, int exam) {

        try {
            gradeRepository.updateGrade(studentId, courseId, usual, exam);
            return ServerResponse.createBySuccessMessage("成绩修改成功");
        } catch (Exception e){
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("数据库错误，修改失败");
        }
    }

    // 修改课程平时成绩占比
    @Override
    public ServerResponse modifUsualRatio(String courseId, int usualRatio) {

        try {
            rationRepository.updateRatio(courseId, usualRatio);
            return ServerResponse.createBySuccessMessage("修改成功");
        } catch (Exception e){
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("数据库错误，修改失败");
        }

    }

    /**** 学生功能 ****/

    // 查询个人所有成绩
    @Override
    public ServerResponse getGradesList(String studentId) {

        // 按平时分比例整合后的最终成绩
        List<StuGradeVO> stuGrades = new ArrayList<>();

        try {
            List<CourseGrade> grades = gradeRepository.getCourseGradesByStudentId(studentId);

            for (CourseGrade grade:grades){
                StuGradeVO stuGrade = new StuGradeVO();
                stuGrade.setStudentId(grade.getStudentId());
                stuGrade.setCourseId(grade.getCourseId());
                stuGrade.setUsualGrade(grade.getUsual());
                stuGrade.setExamGrade(grade.getExam());
                Course course = courseRepository.findByCourseId(grade.getCourseId());
                stuGrade.setCourseName(course.getCourseName());
                //整合成绩
                int ratio = rationRepository.getCourseRatioByCourseId(grade.getCourseId());
                double finalGrade = grade.getUsual() * ratio / 100  + grade.getExam() * (100-ratio) / 100;
                stuGrade.setFinalGrade(finalGrade);
                stuGrades.add(stuGrade);
            }
            return ServerResponse.createBySuccess("获取成绩成功", stuGrades);
        } catch (Exception e){
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("数据库错误，获取失败");
        }

    }

    // 查询个人单科成绩
    @Override
    public ServerResponse getGrade(String studentId, String courseId) {

        StuGradeVO stuGrade = new StuGradeVO();

        try {
            CourseGrade grade = gradeRepository.getCourseGradeByStudentIdAndCourseId(studentId, courseId);
            stuGrade.setStudentId(grade.getStudentId());
            stuGrade.setCourseId(grade.getCourseId());
            Course course = courseRepository.findByCourseId(grade.getCourseId());
            stuGrade.setCourseName(course.getCourseName());

            //整合成绩
            int ratio = rationRepository.getCourseRatioByCourseId(grade.getCourseId());
            double finalGrade = grade.getUsual() * ratio / 100  + grade.getExam() * (100-ratio) / 100;
            stuGrade.setFinalGrade(finalGrade);
            return ServerResponse.createBySuccess("获取成绩成功", stuGrade);

        } catch (Exception e){
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("数据库错误，获取失败");
        }

    }
}
