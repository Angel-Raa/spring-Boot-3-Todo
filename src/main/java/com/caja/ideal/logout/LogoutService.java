package com.caja.ideal.logout;

import com.caja.ideal.token.ITokenRepository;
import com.caja.ideal.token.TokenModel;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
public class LogoutService implements LogoutHandler {
    @Autowired
    private ITokenRepository tokenRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String authHeader = request.getHeader("Authorization");
        final String email;
        if (authHeader == null || !authHeader.startsWith("Authorization")){
            return;
        }
        String jwt = authHeader.substring(7);
        TokenModel storeToken = tokenRepository.findByToken(jwt)
                .orElse(null);
        if(storeToken != null){
            storeToken.setExpired(true);
            storeToken.setRevoked(true);
            tokenRepository.save(storeToken);
        }
    }
}
