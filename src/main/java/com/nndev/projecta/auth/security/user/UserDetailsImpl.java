package com.nndev.projecta.auth.security.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.nndev.projecta.member.entity.Member;

import java.util.Collection;
import java.util.Set;

import lombok.Builder;

public class UserDetailsImpl implements UserDetails {
    private final Long id;
    private final String email;
    private final String password;
    private final String name;
    private Collection<? extends GrantedAuthority> authorities;

    @Builder
    public UserDetailsImpl(Long id, String email, String password, String name, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public static UserDetailsImpl from(Member member) {
        /**
         * 추후에 역할 권한이 필요하면 추가로 작성한다.
         * Set<MemberRole> roleTypes = Set.of(member.getMemberRole());
         */
        return UserDetailsImpl.builder()
                .id(member.getId())
                .email(member.getEmail())
                .password(member.getPassword())
                .name(member.getName())
                .authorities(Set.of())
                .build();
    }
}
