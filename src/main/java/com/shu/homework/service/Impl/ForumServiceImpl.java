package com.shu.homework.service.Impl;

import com.shu.homework.common.ServerResponse;
import com.shu.homework.entity.Comment;
import com.shu.homework.entity.Question;
import com.shu.homework.respository.CommentRepository;
import com.shu.homework.respository.QuestionRepository;
import com.shu.homework.respository.UserRepository;
import com.shu.homework.service.ForumService;
import com.shu.homework.vo.CommentVO;
import com.shu.homework.vo.ForumVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class ForumServiceImpl implements ForumService {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public ServerResponse publishQuestion(String title, String creator, String content) {

        Question question = new Question();
        question.setTitle(title);
        question.setCreator(creator);
        question.setContent(content);
        question.setCommentCount(0); // 刚发布时回复数为0
        question.setTime(new Date(System.currentTimeMillis()));

        try {
            questionRepository.save(question);
            return ServerResponse.createBySuccessMessage("问题发布成功");
        }catch (Exception e){
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("系统错误，发布失败");
        }

    }

    @Override
    public ServerResponse answer(Long parentId, String comentator, String content) {

        Comment comment = new Comment();
        comment.setParentId(parentId);
        comment.setCommentator(comentator);
        comment.setContent(content);
        comment.setTime(new Date(System.currentTimeMillis()));

        try {
            commentRepository.save(comment);
            // 问题评论数加一
            questionRepository.updateCommentCount(parentId);
            return ServerResponse.createBySuccessMessage("回复成功");
        }catch (Exception e){
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("系统错误，回复失败");
        }

    }

    @Override
    public ServerResponse<Question> getQuestionsByCreator(String creator) {

        ServerResponse serverResponse;
        try{
            List<Question> questions = questionRepository.getQuestionsByCreator(creator);
            serverResponse = ServerResponse.createBySuccess("success",questions);
        } catch (Exception e){
            e.printStackTrace();
            serverResponse = ServerResponse.createByErrorMessage("系统错误");
        }

        return serverResponse;

    }

    @Override
    public ServerResponse<Comment> getCommentsByQuestion(Long parentId) {

        ServerResponse serverResponse;
        try{
            List<Comment> comments = commentRepository.getCommentsByParentId(parentId);
            serverResponse = ServerResponse.createBySuccess("success",comments);
        } catch (Exception e){
            e.printStackTrace();
            serverResponse = ServerResponse.createByErrorMessage("数据库错误");
        }

        return serverResponse;

    }

    @Override
    public ServerResponse<Question> getAllQuestions() {

        ServerResponse serverResponse;
        try {
            List<Question> questions = questionRepository.getAllQuestions();
            serverResponse = ServerResponse.createBySuccess("success", questions);
        } catch (Exception e){
            e.printStackTrace();
            serverResponse = ServerResponse.createByErrorMessage("数据库错误");
        }
        return serverResponse;
    }

    @Override
    public ServerResponse deleteQuestion(Long id, String creator) {

        Question question = questionRepository.getOne(id);
        if(question == null){
            return ServerResponse.createByErrorMessage("问题不存在，删除失败");
        }
        if(creator.equals(question.getCreator())){ //是本人发布的问题
            try{
                questionRepository.delete(question);
                // 删除该问题下的所有评论
                List<Comment> commentList = commentRepository.getCommentsByParentId(id);
                for(Comment comment:commentList){
                    commentRepository.delete(comment);
                }
                return ServerResponse.createBySuccessMessage("删除成功");
            } catch (Exception e){
                e.printStackTrace();
                return ServerResponse.createByErrorMessage("删除失败");
            }

        }
        return ServerResponse.createByErrorMessage("删除失败");
    }

    @Override
    public ServerResponse deleteComment(Long id, String commentator) {

        Comment comment = commentRepository.getOne(id);
        if(comment == null){
            return ServerResponse.createByErrorMessage("评论不存在，删除失败");
        }
        if(commentator.equals(comment.getCommentator())){ // 是本人的评论
            commentRepository.delete(comment);
            return ServerResponse.createBySuccessMessage("删除成功");
        }
        return ServerResponse.createByErrorMessage("删除失败");
    }

    @Override
    public ServerResponse getQuestionsById(Long questionId) {
        Question question = questionRepository.getOne(questionId);
        return ServerResponse.createBySuccess(setForumVO(question));
    }

    @Override
    public ServerResponse submitAnswer(Long id, Long questionId, String content) {
        Comment comment = new Comment();
        String creator = userRepository.findByUserId(id).getName();
        comment.setParentId(0L);
        comment.setCommentator(creator);
        comment.setQuestionId(questionId);
        comment.setContent(content);
        comment.setTime(new Date(System.currentTimeMillis()));
        try {
            commentRepository.save(comment);
        } catch (Exception e) {
            return ServerResponse.createByErrorMessage("回复失败");
        }
        return ServerResponse.createBySuccessMessage("回复成功");
    }

    public ForumVO setForumVO (Question question) {
        ForumVO forumVO = new ForumVO();
        forumVO.setTitle(question.getTitle());
        forumVO.setQuestionId(question.getId());
        forumVO.setContent(question.getContent());
        forumVO.setCreator(question.getCreator());
        forumVO.setCommentCount(question.getCommentCount());
        forumVO.setTime(question.getTime());
        //获取回复
        List<Comment> comments = commentRepository.findAllByQuestionId(question.getId());
        //获取回复的评论
        List<CommentVO> commentVOS = new ArrayList<>();
        for (Comment comment : comments) {
            //首先获取所有不是子评论的回复添加到列表中
            if(comment.getParentId() == 0) {
                CommentVO commentVO = new CommentVO(comment);
                commentVOS.add(commentVO);
                //获取回复中所有评论的子评论
                List<Comment> subComments = new ArrayList<>();
                for (Comment subComment: comments) {
                    if (subComment.getParentId().equals(comment.getId())) {
                        subComments.add(subComment);
                    }
                }
                commentVO.setSubComment(subComments);
            }
        }
        forumVO.setCommentList(commentVOS);
        return forumVO;
    }
}
