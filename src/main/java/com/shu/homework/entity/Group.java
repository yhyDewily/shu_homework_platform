package com.shu.homework.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "student_group")
public class Group implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer size;
    private String name;
    private String captain;
    private String member;
    private String courseId;



}
