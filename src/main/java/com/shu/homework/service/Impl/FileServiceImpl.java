package com.shu.homework.service.Impl;

import com.shu.homework.common.Const;
import com.shu.homework.common.ServerResponse;
import com.shu.homework.entity.Homework;
import com.shu.homework.entity.Material;
import com.shu.homework.entity.User;
import com.shu.homework.respository.HomeworkRepository;
import com.shu.homework.respository.MaterialRepository;
import com.shu.homework.respository.UserRepository;
import com.shu.homework.service.FileService;
import com.shu.homework.util.FileUploadUtils;
import com.shu.homework.util.FileUtils;
import com.shu.homework.vo.HomeworkVO;
import com.shu.homework.vo.MaterialVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private HomeworkRepository homeworkRepository;
    @Autowired
    private MaterialRepository materialRepository;
    @Autowired
    private UserRepository userRepository;

    /**
     * 学生上传作业
     * @param studentId 谁交的
     * @param teacherId 交给谁
     */
    @Override
    public ServerResponse uploadHomework(MultipartFile file, Long studentId, Long teacherId) {
        User student = userRepository.findByUserId(studentId);
        User teacher = userRepository.findByUserId(teacherId);

        String originalFilename = file.getOriginalFilename();
        if(originalFilename == null || "".equals(originalFilename)) {
            return ServerResponse.createByErrorMessage("文件名不能为空");
        }
        //若该学生已上传作业，先删除旧版本
        Homework homework = homeworkRepository.getHomeworkByStudentIdAndTeacherId(student.getMail(), teacher.getMail());
        if (homework != null){
            String fileName = Const.HOMEWORK_DIR + homework.getFileName();
            try {
                FileUtils.deleteFile(fileName);
                homeworkRepository.delete(homework);
            } catch (Exception e){
                e.printStackTrace();
                return ServerResponse.createByErrorMessage("系统错误");
            }
        }

        // 上传文件
        try {
            FileUploadUtils.upload(Const.HOMEWORK_DIR, file);
        } catch (Exception e){
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("系统错误");
        }
        // 存入数据库
        Homework newHomeWork = new Homework();
        newHomeWork.setFileName(file.getOriginalFilename());
        newHomeWork.setStudentId(student.getMail());
        newHomeWork.setTeacherId(teacher.getMail());
        newHomeWork.setTime(new Date(System.currentTimeMillis()));
        try{
            homeworkRepository.save(newHomeWork);
            return ServerResponse.createBySuccessMessage("作业上传成功");
        }catch (Exception e){
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("数据库错误");
        }

    }

    // 老师上传资料
    @Override
    public ServerResponse uploadMaterial(MultipartFile file, Long teacherId) {
        User teacher = userRepository.findByUserId(teacherId);
        String originalFilename = file.getOriginalFilename();
        if(originalFilename == null || "".equals(originalFilename)) {
            return ServerResponse.createByErrorMessage("文件名不能为空");
        }

        // 上传文件
        try {
            FileUploadUtils.upload(Const.MATERAIL_DIR, file);
        } catch (Exception e){
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("系统错误");
        }

        // 存入数据库
        Material material = new Material();
        material.setFileName(file.getOriginalFilename());
        material.setUploader(teacher.getMail());
        material.setTime(new Date(System.currentTimeMillis()));
        try{
            materialRepository.save(material);
            return ServerResponse.createBySuccessMessage("资料上传成功");
        }catch (Exception e){
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("数据库错误");
        }
    }

    // 老师下载作业
    @Override
    public ServerResponse downloadHomework(HttpServletResponse response, String fileName) {
/*
        String filePath = Const.HOMEWORK_DIR + fileName;
        try {
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            FileUtils.writeBytes(filePath, response.getOutputStream());
            return ServerResponse.createBySuccessMessage("下载成功");
        } catch (Exception e){
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("下载错误");
        }
*/
        // 配置文件下载
        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        // 下载文件能正常显示中文
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }


        String filePath = Const.HOMEWORK_DIR + fileName;
        File file = new File(filePath);
        // 实现文件下载
        byte[] buffer = new byte[1024];
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        try {
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            OutputStream os = response.getOutputStream();
            int i = bis.read(buffer);
            while (i != -1) {
                os.write(buffer, 0, i);
                i = bis.read(buffer);
            }
            System.out.println("Download the song successfully!");
        }
        catch (Exception e) {
            System.out.println("Download the song failed!");
        }
        finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    // 学生下载资料
    @Override
    public ServerResponse downloadMaterial(HttpServletResponse response, String fileName) {

        String filePath = Const.MATERAIL_DIR + fileName;
        try {
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            FileUtils.writeBytes(filePath, response.getOutputStream());

            return ServerResponse.createBySuccessMessage("下载成功");
        } catch (Exception e){
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("下载错误");
        }

    }

    // 返回已有资料列表
    @Override
    public ServerResponse getAllMaterials(Long userId){

        List<Material> materials = materialRepository.findAll();
        // 返回的视图
        List<MaterialVO> materialVOList = new ArrayList<>();

        User user = userRepository.findByUserId(userId);

        try{
            for (Material material:materials){
                MaterialVO materialVO = new MaterialVO();
                materialVO.setFileName(material.getFileName());
                materialVO.setTime(material.getTime());
                String mail = material.getUploader();
                User teacher = userRepository.findByMail(material.getUploader());
                materialVO.setTeacherName(teacher.getName());

                materialVOList.add(materialVO);
            }
            return ServerResponse.createBySuccess("success",materialVOList);
        }catch (Exception e){
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("数据库错误");
        }

    }

    // 返回某老师收到的作业列表
    @Override
    public ServerResponse getReceivedHomeworks(Long teacherId){
        User teacher = userRepository.findByUserId(teacherId);
        List<Homework> homeworks = homeworkRepository.getHomeworksByTeacherId(teacher.getMail());
        // 返回的视图
        List<HomeworkVO> homeworkVOList = new ArrayList<>();

        try {
            for(Homework homework:homeworks){
                HomeworkVO homeworkVO = new HomeworkVO();
                homeworkVO.setFileName(homework.getFileName());
                homeworkVO.setTime(homework.getTime());

                User student = userRepository.findByMail(homework.getStudentId());
                homeworkVO.setStudentName(student.getName());

                homeworkVOList.add(homeworkVO);
            }
            return ServerResponse.createBySuccess("success", homeworkVOList);
        }catch (Exception e){
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("数据库错误");
        }
    }


}