package com.shu.homework.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "scourse")
public class SCourse {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String courseId;
    @Column(name = "studentId")
    private Long studentId;
    private String status;

}
