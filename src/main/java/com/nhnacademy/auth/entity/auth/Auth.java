package com.nhnacademy.auth.entity.auth;

import com.nhnacademy.auth.entity.memberAuth.MemberAuth;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Auth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Size(min = 1, max = 50)
    private String name;

    @OneToMany(mappedBy = "auth",cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MemberAuth> memberAuthSet;


}
