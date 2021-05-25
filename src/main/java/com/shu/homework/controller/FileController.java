package com.shu.homework.controller;

import com.shu.homework.common.Const;
import com.shu.homework.common.ServerResponse;
import com.shu.homework.service.FileService;
import com.shu.homework.service.Impl.FileServiceImpl;
import com.shu.homework.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/file/")
public class FileController {

    @Autowired
    private FileServiceImpl fileService;

    /**
     * 学生上传作业
     * @param session 获取studentId 谁交的
     * @param teacherId 交给谁
     */
    @RequestMapping(value = "upload_homework", method = RequestMethod.POST)
    @CrossOrigin
    public ServerResponse uploadHomework(MultipartFile file, Long teacherId, HttpSession session) {
        UserVO currentUser = (UserVO) session.getAttribute(Const.CURRENT_USER);
        return fileService.uploadHomework(file, currentUser.getId(), teacherId);
    }

    // 老师上传资料
    @RequestMapping(value = "upload_material", method = RequestMethod.POST)
    @CrossOrigin
    public ServerResponse uploadMaterial(MultipartFile file, HttpSession session) {
        UserVO currentUser = (UserVO) session.getAttribute(Const.CURRENT_USER);
        if(currentUser == null) return ServerResponse.createByErrorMessage("用户未登录");
        return fileService.uploadMaterial(file, currentUser.getId());
    }

    // 老师下载作业
    @RequestMapping(value = "download_homework", method = {RequestMethod.POST, RequestMethod.GET})
    @CrossOrigin
    public ServerResponse downloadHomework(HttpServletResponse response, String fileName){

        return fileService.downloadHomework(response, fileName);
    }

    // 学生下载资料
    @RequestMapping(value = "download_material", method ={ RequestMethod.POST, RequestMethod.GET })
    @CrossOrigin
    public ServerResponse downloadMaterial(HttpServletResponse response, String fileName){

        return fileService.downloadMaterial(response, fileName);
    }

    // 返回已发布资料列表
    @RequestMapping(value = "get_all_materials", method = RequestMethod.POST)
    @CrossOrigin
    public ServerResponse getAllMaterials(Long userId){

        return fileService.getAllMaterials(userId);
    }

    // 返回某老师收到的作业列表
    @RequestMapping(value = "get_received_homeworks", method = RequestMethod.POST)
    @CrossOrigin
    public ServerResponse getReceivedHomeworks(HttpSession session){
        UserVO currentUser = (UserVO) session.getAttribute(Const.CURRENT_USER);
        return fileService.getReceivedHomeworks(currentUser.getId());
    }

}
