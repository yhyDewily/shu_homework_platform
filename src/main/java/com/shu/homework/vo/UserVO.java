package com.shu.homework.vo;

import com.shu.homework.entity.User;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserVO implements Serializable {

    private static final long serialVersionUID = -7114049342051831714L;

    private Long id;
    private String mail;
    private String name;
    private String password;
    //1.学生 2.教师
    private int role;

    public UserVO(Long id, String mail, String name, String password, int role) {
        this.id = id;
        this.mail = mail;
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public UserVO(User user) {
        this.id = user.getId();
        this.mail = user.getMail();
        this.name = user.getName();
        this.role = user.getRole();
    }
}
