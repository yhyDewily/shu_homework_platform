package com.shu.homework.controller;

import com.shu.homework.common.Const;
import com.shu.homework.common.ServerResponse;
import com.shu.homework.entity.Comment;
import com.shu.homework.entity.Question;
import com.shu.homework.service.ForumService;
import com.shu.homework.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 *  论坛功能controller
 */
@RestController
@RequestMapping("/forum/")
public class ForumController {

    @Autowired
    ForumService forumService;

    // 发布问题
    @RequestMapping(value = "publish_question", method = RequestMethod.POST)
    @CrossOrigin
    public ServerResponse publishQuestion(String title, String content, HttpSession session){
        UserVO userVO = (UserVO) session.getAttribute(Const.CURRENT_USER);
        String creator = userVO.getName();
        if(creator == null){ //未登录
            return ServerResponse.createByErrorMessage("请先登录");
        } else {
            return forumService.publishQuestion(title, creator, content);
        }
    }

    // 对某问题做出回复
    @RequestMapping(value = "answer", method = RequestMethod.POST)
    @CrossOrigin
    public ServerResponse answer(Long parentId, String content, HttpSession session){

        String commentator = session.getAttribute(Const.CURRENT_USER).toString();
        if(commentator == null){ //未登录
            return ServerResponse.createByErrorMessage("请先登录");
        } else {
            return forumService.answer(parentId, commentator, content);
        }
    }

    @RequestMapping(value = "submit_answer", method = RequestMethod.POST)
    @CrossOrigin
    @ResponseBody
    public ServerResponse submitAnswer(HttpSession session, Long questionId, String content) {
        UserVO currentUser = (UserVO) session.getAttribute(Const.CURRENT_USER);
        if(currentUser == null) return ServerResponse.createByErrorMessage("用户未登录");
        return forumService.submitAnswer(currentUser.getId(), questionId, content);
    }

    @RequestMapping(value = "get_question_by_id", method = RequestMethod.POST)
    @CrossOrigin
    public ServerResponse getQuestionById(HttpSession session,Long questionId) {
        UserVO currentUser = (UserVO) session.getAttribute(Const.CURRENT_USER);
        if(currentUser == null) return ServerResponse.createByErrorMessage("用户未登录");
        return forumService.getQuestionsById(questionId);
    }

    // 获取某人发布的所有问题
    @RequestMapping(value = "get_questions_by_creator", method = RequestMethod.POST)
    @CrossOrigin
    public ServerResponse getQuestionsByCreator(HttpSession session){

        String creator = session.getAttribute(Const.CURRENT_USER).toString();
        if(creator == null){ //未登录
            return ServerResponse.createByErrorMessage("请先登录");
        } else {
            return forumService.getQuestionsByCreator(creator);
        }
    }

    // 获取某一问题下的所有回复
    @RequestMapping(value = "get_comments", method = RequestMethod.POST)
    @CrossOrigin
    public ServerResponse getComments(Long parentId){

        return forumService.getCommentsByQuestion(parentId);
    }

    // 获取所有已发布的问题
    @RequestMapping(value = "get_all_questions", method = RequestMethod.POST)
    @CrossOrigin
    public ServerResponse getAllQuestions(){

        return forumService.getAllQuestions();

    }

    // 删除自己发布的问题
    @RequestMapping(value = "delete_question", method = RequestMethod.POST)
    @CrossOrigin
    public ServerResponse deleteQuestion(Long id, HttpSession session){
        String creator = session.getAttribute(Const.CURRENT_USER).toString();
        if(creator == null){ //未登录
            return ServerResponse.createByErrorMessage("请先登录");
        } else {
            return forumService.deleteQuestion(id, creator);
        }
    }

    // 删除自己发布的评论
    @RequestMapping(value = "delete_comment", method = RequestMethod.POST)
    @CrossOrigin
    public ServerResponse deleteComment(Long id, HttpSession session){
        String commentator = session.getAttribute(Const.CURRENT_USER).toString();
        if(commentator == null){ //未登录
            return ServerResponse.createByErrorMessage("请先登录");
        } else {
            return forumService.deleteComment(id, commentator);
        }
    }

}
