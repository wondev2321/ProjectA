package com.nndev.projecta.member.dto;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MemberDto {

    private Long id;
    private String Name;
    private String password;
    private String profileGreetings;
    private String profileUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean activated;
}
