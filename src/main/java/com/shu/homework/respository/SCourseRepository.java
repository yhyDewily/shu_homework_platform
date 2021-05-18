package com.shu.homework.respository;

import com.shu.homework.entity.SCourse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SCourseRepository extends JpaRepository<SCourse, Long> {

    SCourse findByCourseIdAndStudentId(String courseId, Long id);

    Page<SCourse> findByStudentId(Long studentId, Pageable pageable);

    List<SCourse> findByStudentId(Long studentId);
}
