package com.nhnacademy.auth.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Auth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Size(min = 1, max = 50)
    private String name;

    @OneToMany(mappedBy = "auth",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberAuth> memberAuthSet;

}
