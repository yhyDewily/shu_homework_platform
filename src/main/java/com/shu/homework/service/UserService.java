package com.shu.homework.service;

import com.shu.homework.common.ServerResponse;
import com.shu.homework.entity.User;
import com.shu.homework.respository.UserRepository;
import com.shu.homework.vo.UserVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    ServerResponse<UserVO> login(String username, String password);

    ServerResponse<UserVO> getInformation(Long id);

    ServerResponse<String> register(User user);

    ServerResponse<UserVO> getByMail(String mail);

    ServerResponse updateInformation(User user);

    ServerResponse updatePassword(Long id, String new_password, String old_password);
}
