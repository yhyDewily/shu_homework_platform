package com.shu.homework.vo;

import lombok.Data;

import java.sql.Date;

/**
 * 返回老师上传的资料实体
 */
@Data
public class MaterialVO {

    private String fileName;
    private String teacherName;
    private Date time;
}
