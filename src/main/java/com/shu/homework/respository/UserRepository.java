package com.shu.homework.respository;

import com.shu.homework.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {


    @Query(value = "select count(1) from ems.user where name =?1", nativeQuery = true)
    int checkUsername(String username);

    @Query(value = "select count(1) from ems.user where mail=?1", nativeQuery = true)
    int checkEmail(String email);

    @Query(value = "select count(1) from ems.user where password=?1 and id=?2", nativeQuery = true)
    int checkPassword(String md5EncodeUtf8, Long id);
//    @Query(value = "select count(1) from ems.user where mail=?1 and id=?2", nativeQuery = true)
//    int checkEmailByUserId(String email, Long id);

    @Query(value = "SELECT * from ems.user where id=?1", nativeQuery = true)
    User findByUserId(Long id);

    User findByNameAndPassword(String username, String password);

    User findByMail(String mail);

    User findByName(String username);

    List<User> getAllByRole(Integer role);
}
