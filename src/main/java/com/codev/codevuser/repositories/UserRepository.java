package com.codev.codevuser.repositories;

import com.codev.codevuser.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<UserEntity,Integer> {

    @Query("select ut from UserEntity ut where ut.login = ?1")
    UserEntity searchLogin(String login);

}
