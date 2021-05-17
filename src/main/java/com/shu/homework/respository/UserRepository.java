package com.shu.homework.respository;

import com.shu.homework.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Integer> {


    @Query(value = "select count(1) from ems.user where name =?1", nativeQuery = true)
    int checkUsername(String username);

    @Query(value = "SELECT * from ems.user where id=?1", nativeQuery = true)
    User findByUserId(Long id);

    User findByNameAndPassword(String username, String password);

    @Query(value = "select count(1) from ems.user where mail=?1", nativeQuery = true)
    int checkEmail(String email);

    User findByMail(String mail);
}
