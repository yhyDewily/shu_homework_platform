package com.shu.homework.service.Impl;

import com.shu.homework.common.Const;
import com.shu.homework.common.ServerResponse;
import com.shu.homework.entity.User;
import com.shu.homework.respository.UserRepository;
import com.shu.homework.service.UserService;
import com.shu.homework.util.MD5Util;
import com.shu.homework.vo.UserVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository repository;

    @Override
    public ServerResponse<UserVO> login(String username, String password) {
        int resultCount = repository.checkUsername(username);
        if (resultCount == 0) return ServerResponse.createByErrorMessage("用户名不存在");
        String md5Password = MD5Util.MD5EncodeUtf8(password);
        User user = repository.findByNameAndPassword(username, md5Password);

        if (user == null) {
            return ServerResponse.createByErrorMessage("密码错误");
        }
        UserVO userVO = new UserVO(user);
        userVO.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess("登录成功", userVO);
    }

    @Override
    public ServerResponse<UserVO> getInformation(Long id) {
        User user = repository.findByUserId(id);
        if(user == null) return ServerResponse.createByErrorMessage("找不到当前用户");
        UserVO userVO = new UserVO(user);
        userVO.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess(userVO);
    }

    @Override
    public ServerResponse<String> register(User user) {
        ServerResponse validResponse = this.checkValid(user.getName(), Const.USERNAME);
        if(!validResponse.isSuccess()) return validResponse;
        validResponse = this.checkValid(user.getMail(), Const.EMAIL);
        if(!validResponse.isSuccess()) return validResponse;
        user.setRole(Const.Role.ROLE_STUDENT);
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));

        try {
            repository.save(user);
        } catch (Exception e) {
            return ServerResponse.createByErrorMessage("注册失败");
        }
        return ServerResponse.createBySuccessMessage("注册成功");
    }

    @Override
    public ServerResponse<UserVO> getByMail(String mail) {
        User user = repository.findByMail(mail);
        if(user == null) return ServerResponse.createByErrorMessage("邮箱不存在");
        UserVO userVO = new UserVO(user);
        userVO.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess(userVO);
    }

    @Override
    public ServerResponse updateInformation(User user) {
        User new_user = repository.findByUserId(user.getId());
        new_user.setName(user.getName());
        new_user.setMail(user.getMail());
        try {
            repository.save(new_user);
        } catch (Exception e) {
            return ServerResponse.createByErrorMessage("修改信息失败");
        }
        return ServerResponse.createBySuccessMessage("修改信息成功");
    }

    @Override
    public ServerResponse updatePassword(Long id, String new_password, String old_password) {
        return null;
    }

    private ServerResponse checkValid(String str, String type) {
        if(StringUtils.isNotBlank(type)) {
            if(Const.USERNAME.equals(type)) {
                int resultCount = repository.checkUsername(str);
                if(resultCount > 0) return ServerResponse.createByErrorMessage("用户已存在");
            }
            if(Const.EMAIL.equals(type)) {
                int resultCount = repository.checkEmail(str);
                if(resultCount > 0) return ServerResponse.createByErrorMessage("email已存在");
            }
        } else return ServerResponse.createByErrorMessage("参数错误");
        return ServerResponse.createBySuccessMessage("校验成功");
    }


}
