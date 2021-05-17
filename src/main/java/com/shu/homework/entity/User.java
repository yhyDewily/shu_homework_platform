package com.shu.homework.entity;

import com.shu.homework.vo.UserVO;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "user")
public class User implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mail;
    private String name;
    private String password;
    //0.学生 1.教师
    private int role;

    public User(UserVO userVO) {
        this.id = userVO.getId();
        this.mail = userVO.getMail();
        this.name = userVO.getName();
        this.password = userVO.getPassword();
        this.role = userVO.getRole();
    }

    public User() {

    }
}
