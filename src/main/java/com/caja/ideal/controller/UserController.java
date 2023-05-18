package com.caja.ideal.controller;

import com.caja.ideal.auth.AuthenticateResponse;
import com.caja.ideal.auth.AuthenticateService;
import com.caja.ideal.mapper.UserMapper;
import com.caja.ideal.request.AuthenticationRequest;
import com.caja.ideal.request.RegisterRequest;
import com.caja.ideal.request.RegistrationRequest;
import com.caja.ideal.service.IUserService;
import com.caja.ideal.user.UserModel;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private AuthenticateService authenticateService;
    @Autowired
    private IUserService service;

    @GetMapping("/all")
    public ResponseEntity<List<UserMapper>> all(){
        return ResponseEntity.ok(service.allUser());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserModel> user(@PathVariable @Min(1) Long id){
        return ResponseEntity.ok(service.user(id));
    }

    @PostMapping("/add")
    public ResponseEntity<UserModel> savePost(@Valid @RequestBody UserModel user){
        return new ResponseEntity<>(service.save(user), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable @Min(1) Long id){
        return ResponseEntity.ok(service.delete(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserModel> updatePut(@Valid @RequestBody UserModel user, @PathVariable @Min(1) Long id){
        return ResponseEntity.ok(service.update(user, id));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticateResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(authenticateService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticateResponse> authenticate(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(authenticateService.authenticate(request));
    }

    // Demo
    @PostMapping("/demo")
    public String registerUser(RegistrationRequest request){
        UserModel userModel = service.registerUser(request);
        return """
                Success! Please, check your email to complete your registration
                """;
    }

}
