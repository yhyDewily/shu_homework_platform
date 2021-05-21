package com.shu.homework.respository;

import com.shu.homework.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GroupRepository extends JpaRepository<Group, Long> {

    @Query(value = "select count(1) from ems.student_group where member regexp '?1'", nativeQuery = true)
    int checkGroupMember(String member);

    //@Query(value = "select * from ems.student_group where id=?1", nativeQuery = true)
    Group getById(Long id);

    @Query(value = "select * from ems.group where memmber regexp'?1'", nativeQuery = true)
    Group getGroupByMemberAndCourseId(String member, String courseId);
}
