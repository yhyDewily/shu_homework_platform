package com.shu.homework.vo;

import com.shu.homework.entity.Comment;
import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
public class ForumVO {

    private Long questionId;
    private String title;
    private String content;
    private String creator;
    private Integer commentCount;
    private Date time;
    //回复列表
    private List<CommentVO> commentList;

}
