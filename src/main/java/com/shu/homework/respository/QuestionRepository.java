package com.shu.homework.respository;

import com.shu.homework.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query(value = "select * from ems.question order by time desc", nativeQuery = true)
    public List<Question> getAllQuestions();

    @Query(value = "select * from ems.question where creator=?1 order by time desc", nativeQuery = true)
    public List<Question> getQuestionsByCreator(String creator);

    @Modifying
    @Transactional
    @Query(value = "update ems.question set commentCount=commentCount+1 where id=?1", nativeQuery = true)
    public void updateCommentCount(Long parentId);

}
