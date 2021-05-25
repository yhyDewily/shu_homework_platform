package com.shu.homework.vo;

import lombok.Data;

import java.sql.Date;

@Data
public class HomeworkVO {

    private String fileName;
    private String studentName;
    private Date time;

}