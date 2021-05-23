package com.shu.homework.vo;

import com.shu.homework.entity.Comment;
import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
public class CommentVO {

    private Long id;
    private Long parentId;
    private Long questionId;
    private String commentator;
    private String content;
    private Date time;
    //对于回复的评论
    private List<Comment> subComment;

    public CommentVO() {
    }

    public CommentVO(Comment comment) {
        this.id = comment.getId();
        this.parentId = comment.getParentId();
        this.questionId = comment.getQuestionId();
        this.commentator = comment.getCommentator();
        this.content = comment.getContent();
        this.time = comment.getTime();
    }
}
