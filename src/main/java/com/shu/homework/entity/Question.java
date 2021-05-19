package com.shu.homework.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "question")
public class Question {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private String creator;
    private Integer commentCount;

}
