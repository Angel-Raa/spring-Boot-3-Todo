package com.caja.ideal.service.impl;

import com.caja.ideal.exception.AlreadyExists;
import com.caja.ideal.exception.ResourceNotFound;
import com.caja.ideal.mapper.UserMapper;
import com.caja.ideal.repository.IUserRepository;
import com.caja.ideal.request.RegistrationRequest;
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
        return repository.findAll().stream().map(user -> new UserMapper(user.getEmail(), user.getPassword(), user.getUsername()))
                .collect(Collectors.toList());
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
        return repository.save(user);
    }

    @Transactional
    @ResponseStatus(HttpStatus.OK)
    @Override
    public UserModel update(UserModel user, Long id) {
        UserModel userTo = repository.findById(id).orElseThrow(() -> new ResourceNotFound("this user does not exist"));
        BeanUtils.copyProperties(user, userTo, "id");
        return repository.save(userTo);
    }

    @Transactional(readOnly = true)
    @ResponseStatus(HttpStatus.OK)
    @Override
    public UserModel user(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFound("this user does not exist"));
    }
    @Transactional
    @Override
    public UserModel registerUser(RegistrationRequest request) {
        Optional<UserModel> user = repository.findByEmail(request.email());
        if(user.isPresent()){
            throw new AlreadyExists("User with email ".concat(request.email().concat("Already exists")) );
        }
        UserModel newUser = createUser(request);
        return repository.save(newUser);
    }



    @Transactional
    @Override
    public Optional<UserModel> findEmail(String email) {
        return repository.findByEmail(email);
    }

    private UserModel createUser(RegistrationRequest request) {
        UserModel newUser = new UserModel();
        newUser.setPassword(encoder.encode(request.password()));
        newUser.setUsername(request.username());
        newUser.setEmail(request.email());
        newUser.setRole(request.role());
        return newUser;
    }
}
