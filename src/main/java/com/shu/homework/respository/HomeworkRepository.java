package com.shu.homework.respository;

import com.shu.homework.entity.Homework;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HomeworkRepository extends JpaRepository<Homework, Long> {

    @Query(value = "select * from ems.homework", nativeQuery = true)
    List<Homework> getAll();

    List<Homework> findByCourseId(String courseId);

    Homework findByCourseIdAndStudentId(String courseId, Long studentId);


}
