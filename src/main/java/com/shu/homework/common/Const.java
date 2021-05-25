package com.shu.homework.common;

public class Const {

    public static final String CURRENT_USER = "currentUser";
    public static final String EMAIL = "email";
    public static final String USERNAME = "username";

    // 教师资料路径
    public final static String MATERAIL_DIR = "F:\\Files\\Code\\Java\\homework\\src\\main\\resources\\file\\material\\";
    // 学生作业路径
    public final static String HOMEWORK_DIR = "F:\\Files\\Code\\Java\\homework\\src\\main\\resources\\file\\homework\\";

    public interface Role {
        int ROLE_STUDENT = 0;
        int ROLE_TEACHER = 1;
        int ROLE_ADMIN = 2;
    }


}
