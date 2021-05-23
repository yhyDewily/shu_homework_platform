package com.shu.homework.controller;

import com.shu.homework.common.Const;
import com.shu.homework.common.ServerResponse;
import com.shu.homework.service.GradeService;
import com.shu.homework.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 *  成绩管理Controller
 */
@RestController
@RequestMapping("/grade/")
public class GradeController {

    @Autowired
    GradeService gradeService;

    /**** 教师功能 ****/

    // 录入成绩
    @RequestMapping(value = "entry_grade", method = RequestMethod.POST)
    @CrossOrigin
    public ServerResponse entryGrade(String studentId, String courseId, int usual, int exam){

        if(usual > 100){ //成绩范围在0-100
            usual = 100;
        } else if (usual < 0){
            usual = 0;
        }
        if (exam > 100){
            exam = 100;
        } else if (exam < 0){
            exam = 0;
        }
        return gradeService.entryGrade(studentId, courseId, usual, exam);
    }

    // 修改成绩
    @RequestMapping(value = "modify_grade",method = RequestMethod.POST)
    @CrossOrigin
    public ServerResponse modifyGrade(String studentId, String courseId, int usual, int exam){

        if(usual > 100){ //成绩范围在0-100
            usual = 100;
        } else if (usual < 0){
            usual = 0;
        }
        if (exam > 100){
            exam = 100;
        } else if (exam < 0){
            exam = 0;
        }

        return gradeService.modifyGrade(studentId, courseId, usual, exam);
    }

    // 修改课程平时成绩占比
    @RequestMapping(value = "modify_ratio",method = RequestMethod.POST)
    @CrossOrigin
    public ServerResponse modifyRatio(String courseId, int usualRatio){

        if(usualRatio > 100){ //占比范围在0-100
            usualRatio = 100;
        } else if (usualRatio < 0){
            usualRatio = 0;
        }
        return gradeService.modifUsualRatio(courseId, usualRatio);

    }


    /**** 学生功能 ****/

    // 查询个人所有成绩
    @RequestMapping(value = "get_grades_list",method = RequestMethod.POST)
    @CrossOrigin
    @ResponseBody
    public ServerResponse getGradesList(HttpSession session){
        UserVO currentUser = (UserVO) session.getAttribute(Const.CURRENT_USER);
        return gradeService.getGradesList(currentUser.getId().toString());

    }

    // 查询个人单科成绩
    @RequestMapping(value = "get_grade",method = RequestMethod.POST)
    @CrossOrigin
    public ServerResponse getGrade(String courseId, HttpSession session){

        String studentId = session.getAttribute(Const.CURRENT_USER).toString();
        return gradeService.getGrade(studentId, courseId);
    }

    /***************** test ******************/
/*
    @RequestMapping(value = "get_grades_list_test",method = RequestMethod.POST)
    @CrossOrigin
    public ServerResponse getGradesListTest(String studentId){
        return gradeService.getGradesList(studentId);
    }
    // 查询个人单科成绩
    @RequestMapping(value = "get_grade_test",method = RequestMethod.POST)
    @CrossOrigin
    public ServerResponse getGradeTest(String courseId, String studentId){
        return gradeService.getGrade(studentId, courseId);
    }
 */

}
