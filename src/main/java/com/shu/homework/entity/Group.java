package com.shu.homework.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "group")
public class Group {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String captain;
    private String member;
    private String courseId;




}
