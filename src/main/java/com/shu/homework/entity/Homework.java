package com.shu.homework.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "homework")
public class Homework {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String courseId;
    private String hwId;
    private String hwName;
    private String hwIntro;
}
