package com.shu.homework.respository;

import com.shu.homework.entity.CourseRatio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface CourseRationRepository extends JpaRepository<CourseRatio, Long> {

    // 按课程号获取平时分占比
    @Query(value = "select usualRatio from ems.courseRatio where courseId=?1", nativeQuery = true)
    public int getCourseRatioByCourseId(String courseId);

    // 更新平时分占比
    @Modifying
    @Transactional
    @Query(value = "update ems.courseratio set usualRatio=?2 where courseId=?1", nativeQuery = true)
    public void updateRatio(String courseId, int usualRatio);
}
