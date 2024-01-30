package com.nndev.projecta.member.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "Member")
@Table
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member {

    @Id
    @Column(name = "MEMBER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "MEMBER_EMAIL", length = 50)
    private String email;

    @Column(name = "MEMBER_PASSWORD", length = 100)
    private String password;

    @Column(name = "MEMBER_NAME", length = 50)
    private String name;

    @Column(name = "MEMBER_ACTIVATED")
    private boolean activated;

    @Column(name = "MEMBER_PROFILE_GREETING")
    private String profileGreeting;

    @Column(name = "MEMBER_PRORILE_URL")
    private String profileUrl;

}
