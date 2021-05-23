package com.shu.homework.respository;

import com.shu.homework.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = "select * from ems.comment where parentId=?1 order by time desc", nativeQuery = true)
    List<Comment> getCommentsByParentId(Long parentId);

    List<Comment> findAllByQuestionId(Long questionId);
}
