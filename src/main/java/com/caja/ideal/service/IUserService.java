package com.caja.ideal.service;

import com.caja.ideal.mapper.UserMapper;
import com.caja.ideal.user.UserModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IUserService {
    List<UserMapper> allUser();
    ResponseEntity<Object> delete(Long id);
    UserModel save(UserModel user);
    UserModel update(UserModel user, Long id);
    UserModel user(Long id);
}
