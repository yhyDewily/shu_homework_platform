package com.shu.homework.vo;

import lombok.Data;

import java.util.List;

@Data
public class GroupVO {

    private Long id;
    private Integer size;
    private String groupName;
    private String captain;
    private List<String> member;
    private String courseId;
    //0表示否，1表示是
    private Integer isCaptain;
    private Integer isTeacher;

}
