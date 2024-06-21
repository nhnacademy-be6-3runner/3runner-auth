package com.nhnacademy.auth.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
@Builder
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 50)
    private String password;

    @NotNull
    private Long point;

    @NotNull
    @Size(min = 1, max = 10)
    private String name;

    private int age;

    @NotNull
    @Size(min = 1, max = 11)
    private String phone;

    @NotNull
    @Column(unique = true)
    private String email;


    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL,orphanRemoval = true, fetch = FetchType.EAGER)
    private List<MemberAuth> memberAuthSet = new ArrayList<>();

}

