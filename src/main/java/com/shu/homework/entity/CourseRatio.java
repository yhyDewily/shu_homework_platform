package com.shu.homework.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table
@Data
public class CourseRatio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String courseId;
    private Integer usualRatio;

}
