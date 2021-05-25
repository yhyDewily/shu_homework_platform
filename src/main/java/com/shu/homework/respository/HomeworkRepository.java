package com.shu.homework.respository;

import com.shu.homework.entity.Homework;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HomeworkRepository extends JpaRepository<Homework, Long> {

    @Query(value = "select * from ems.homework", nativeQuery = true)
    List<Homework> getAll();

    // 按提交对象（即老师）获取
    @Query(value = "select * from ems.homework where teacherId=?1", nativeQuery = true)
    List<Homework> getHomeworksByTeacherId(String teacherId);

    // 按提交者（即学生）获取
    @Query(value = "select * from ems.homework where teacherId=?1", nativeQuery = true)
    List<Homework> getHomeworksByStudentId(String studentId);

    // 按提交者和提交对象获取
    Homework getHomeworkByStudentIdAndTeacherId(String studenId, String teacherId);

}
