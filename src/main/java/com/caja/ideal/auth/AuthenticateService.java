package com.caja.ideal.auth;

import com.caja.ideal.config.UserDetailsConfig;
import com.caja.ideal.exception.ResourceNotFound;
import com.caja.ideal.jwt.JWTService;
import com.caja.ideal.repository.IUserRepository;
import com.caja.ideal.request.AuthenticationRequest;
import com.caja.ideal.request.RegisterRequest;
import com.caja.ideal.token.ITokenRepository;
import com.caja.ideal.token.TokenModel;
import com.caja.ideal.token.TokenType;
import com.caja.ideal.user.Role;
import com.caja.ideal.user.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthenticateService {
    @Autowired
    private ITokenRepository tokenRepository;
    @Autowired
    private JWTService jwtService;
    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private IUserRepository userRepository;

    @Transactional
    public AuthenticateResponse register(RegisterRequest request) {
        String encoderPassword = encoder.encode(request.getPassword());
        UserModel user = UserModel.builder()
                .role(Role.USER)
                .email(request.getEmail())
                .password(encoderPassword)
                .username(request.getUsername())
                .build();
        String jwtToken = jwtService.generateToken(new UserDetailsConfig(user));
        TokenModel token = TokenModel.builder()
                .userModels(user)
                .revoked(false)
                .expired(false)
                .tokenType(TokenType.BEARER)
                .token(jwtToken)
                .build();
        userRepository.save(user);
        tokenRepository.save(token);
        return AuthenticateResponse.builder()
                .token(token.getToken())
                .build();

    }

    @Transactional
    public AuthenticateResponse authenticate(AuthenticationRequest request) {
        manager.authenticate(new UsernamePasswordAuthenticationToken(request.getPassword(), request.getEmail()));
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFound("Not found email"));
        String jwtToken = jwtService.generateToken(new UserDetailsConfig(user));
        TokenModel token = TokenModel.builder()
                .expired(false)
                .revoked(false)
                .token(jwtToken)
                .userModels(user)
                .tokenType(TokenType.BEARER)
                .build();
        tokenRepository.save(token);
        return AuthenticateResponse.builder()
                .token(jwtToken)
                .build();
    }

}
