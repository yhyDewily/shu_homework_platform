package com.shu.homework.vo;

import lombok.Data;

@Data
public class StuGradeVO {

    private String courseId;
    private String courseName;
    private String studentId;
    private double usualGrade;
    private double examGrade;
    private double finalGrade;
}
