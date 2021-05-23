package com.shu.homework.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table
@Data
public class CourseGrade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String courseId;
    private String studentId;
    private Integer usual;
    private Integer exam;

}