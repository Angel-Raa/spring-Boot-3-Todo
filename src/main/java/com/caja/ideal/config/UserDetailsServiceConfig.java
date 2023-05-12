package com.caja.ideal.config;

import com.caja.ideal.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceConfig implements UserDetailsService {
    @Autowired
    private IUserRepository repository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return repository.findByEmail(email)
                .map(UserDetailsConfig::new)
                .orElseThrow(() -> new UsernameNotFoundException("Not User found "));
    }
}
