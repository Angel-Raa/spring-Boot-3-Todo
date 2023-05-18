package com.caja.ideal.user;

import com.caja.ideal.token.TokenModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;


import java.io.Serializable;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "users")
public class UserModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false, length = 50)
    @NotBlank(message = "field cannot be empty")
    private String username;
    @NotEmpty
    @Column(length = 20)
    private String password;
    @Email
    @NaturalId(mutable = true)
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role;
    private boolean isEnabled = false;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,  fetch = FetchType.LAZY)
    private List<TokenModel> token ;


}
