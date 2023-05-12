package com.caja.ideal.service.impl;

import com.caja.ideal.exception.AlreadyExists;
import com.caja.ideal.exception.ResourceNotFound;
import com.caja.ideal.mapper.UserMapper;
import com.caja.ideal.repository.IUserRepository;
import com.caja.ideal.service.IUserService;
import com.caja.ideal.user.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private IUserRepository repository;
    @Autowired
    private BCryptPasswordEncoder encoder;

    @Transactional(readOnly = true)
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<UserMapper> allUser() {
        List<UserMapper> list = repository.findAll().stream().map(user -> new UserMapper(user.getEmail(), user.getPassword(), user.getUsername()))
                .collect(Collectors.toList());
        return list;
    }

    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public ResponseEntity<Object> delete(Long id) {
        Optional<UserModel> user = repository.findById(id);
        HashMap<String, String> responde = new HashMap<>();
        if (user.isPresent()) {
            repository.deleteById(id);
            responde.put("message", "user deleted successfully");
            return ResponseEntity.ok(responde);
        }
        responde.put("message", "not found");
        return ResponseEntity.badRequest().body(responde);
    }

    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public UserModel save(UserModel user) {
        Optional<UserModel> email = repository.findByEmail(user.getEmail());
        Optional<UserModel> username = repository.findByUsername(user.getUsername());

        if (email.isPresent() || username.isPresent()) {
            throw new AlreadyExists("There is already a user with this email or username ");
        }

        user.setPassword(encoder.encode(user.getPassword()));
        UserModel save = repository.save(user);
        return save;
    }

    @Transactional
    @ResponseStatus(HttpStatus.OK)
    @Override
    public UserModel update(UserModel user, Long id) {
        UserModel userTo = repository.findById(id).orElseThrow(() -> new ResourceNotFound("this user does not exist"));
        BeanUtils.copyProperties(user, userTo, "id");
        UserModel update = repository.save(userTo);
        return update;
    }

    @Transactional(readOnly = true)
    @ResponseStatus(HttpStatus.OK)
    @Override
    public UserModel user(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFound("this user does not exist"));
    }
}
