package com.nhnacademy.auth.entity.memberAuth;

import com.nhnacademy.auth.entity.auth.Auth;
import com.nhnacademy.auth.entity.member.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MemberAuth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    private Member member;

    @NotNull
    @ManyToOne
    private Auth auth;

}