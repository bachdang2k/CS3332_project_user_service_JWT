package com.javadev.demojwt.security.userPrincipal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.javadev.demojwt.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserPrincipal implements UserDetails {

    //hàm biuld 1 thằng user trên hệ thống (static: nằm trên vùng nhớ trên hệ thống)

    private Long id;
    private String firstName;
    private String LastName;
    private String userName;
    private String email;
    private String phone;
    @JsonIgnore
    private String password;
    private String avatar;
    private Collection<? extends GrantedAuthority> roles;

    public UserPrincipal() {}

    public UserPrincipal(Long id, String firstName, String lastName, String userName, String email, String phone, String password, String avatar, Collection<? extends GrantedAuthority> roles) {
        this.id = id;
        this.firstName = firstName;
        LastName = lastName;
        this.userName = userName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.avatar = avatar;
        this.roles = roles;
    }

    //Build Function
    public static UserPrincipal build(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream().map(
                role -> new SimpleGrantedAuthority(role.getName().name())
        ).collect(Collectors.toList());

        return new UserPrincipal(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getUserName(),
                user.getEmail(),
                user.getPhone(),
                user.getPassword(),
                user.getAvatar(),
                authorities // authority tren he thong -> line 40 thay cho cai role cữ
        );
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
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
}
