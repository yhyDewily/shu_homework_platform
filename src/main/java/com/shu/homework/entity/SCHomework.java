package com.shu.homework.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "schomework")
public class SCHomework {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String hwId;
    private String mail;
    private String courseId;
    private BigDecimal grade;
    private String status;
    private String content;
}
