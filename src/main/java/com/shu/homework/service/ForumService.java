package com.shu.homework.service;

import com.shu.homework.common.ServerResponse;
import com.shu.homework.entity.Comment;
import com.shu.homework.entity.Question;

/**
 * 论坛功能的 Service
 */
public interface ForumService {

    // 发布问题
    ServerResponse publishQuestion(String title, String creator, String content);
    // 对某问题做出回复
    ServerResponse answer(Long parentId, String comentator, String content);
    // 获取某人发布的所有问题
    ServerResponse<Question> getQuestionsByCreator(String creator);
    // 获取某一问题下的所有回复
    ServerResponse<Comment> getCommentsByQuestion(Long parentId);
    // 获取所有已发布的问题
    ServerResponse<Question> getAllQuestions();
    // 删除自己发布的问题
    ServerResponse deleteQuestion(Long id, String creator);
    // 删除自己发布的评论
    ServerResponse deleteComment(Long id, String commentator);

    ServerResponse getQuestionsById(Long questionId);

    ServerResponse submitAnswer(Long id, Long questionId, String content);
}
