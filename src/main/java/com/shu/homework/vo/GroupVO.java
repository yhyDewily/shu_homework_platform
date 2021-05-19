package com.shu.homework.vo;

import lombok.Data;

import java.util.List;

@Data
public class GroupVO {

    private Long id;
    private Integer size;
    private String name;
    private String captain;
    private List<String> member;
    private String courseId;


}
