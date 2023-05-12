package com.caja.ideal.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ITokenRepository extends JpaRepository<TokenModel, Long> {
    Optional<TokenModel> findByToken(String token);
}
