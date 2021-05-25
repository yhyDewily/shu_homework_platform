package com.shu.homework.respository;

import com.shu.homework.entity.CourseGrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CourseGradeRepository extends JpaRepository<CourseGrade, Long> {

    // 按学生获取成绩
    @Query(value = "select * from ems.coursegrade where studentId=?1", nativeQuery = true)
    List<CourseGrade> getCourseGradesByStudentId(String studentId);

    // 按学生、课程获取
    @Query(value = "select * from ems.coursegrade where studentId=?1 and courseId=?2", nativeQuery = true)
    CourseGrade getCourseGradeByStudentIdAndCourseId(String studentId, String courseId);

    // 修改成绩
    @Modifying
    @Transactional
    @Query(value = "update ems.coursegrade set usual=?3, exam=?4 where studentId=?1 and courseId=?2", nativeQuery = true)
    void updateGrade(String studentId, String courseId, int usual, int exam);

    List<CourseGrade> findAllByCourseId(String courseId);
}