package com.caja.ideal.token;

import com.caja.ideal.user.UserModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "token")
public class TokenModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean expired;
    @Enumerated(EnumType.STRING)
    private TokenType tokenType;
    private String token;
    private Boolean revoked;
    @ManyToOne
    @JoinColumn(name = "user_token")
    private UserModel userModels;
}
