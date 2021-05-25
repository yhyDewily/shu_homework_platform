package com.shu.homework.service;
import com.shu.homework.common.ServerResponse;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * 文件上传、下载的 Service
 */
public interface FileService {

    /**
     * 学生上传作业
     * @param studentId 谁交的
     * @param teacherId 交给谁
     */
    public ServerResponse uploadHomework(MultipartFile file, Long studentId, Long teacherId);

    // 老师上传资料
    public ServerResponse uploadMaterial(MultipartFile file, Long teacherId);

    // 老师下载作业
    public ServerResponse downloadHomework(HttpServletResponse response, String fileName);

    // 学生下载资料
    public ServerResponse downloadMaterial(HttpServletResponse response, String fileName);

    // 返回已有资料列表
    public ServerResponse getAllMaterials(Long userId);

    // 返回某老师收到的作业列表
    public ServerResponse getReceivedHomeworks(Long teacherId);
}
