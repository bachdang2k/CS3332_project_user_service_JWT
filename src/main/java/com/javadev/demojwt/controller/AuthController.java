package com.javadev.demojwt.controller;

import com.javadev.demojwt.dto.request.SignInForm;
import com.javadev.demojwt.dto.request.SignUpForm;
import com.javadev.demojwt.dto.response.JwtResponse;
import com.javadev.demojwt.dto.response.ResponseMessage;
import com.javadev.demojwt.model.Role;
import com.javadev.demojwt.model.RoleName;
import com.javadev.demojwt.model.User;
import com.javadev.demojwt.security.jwt.JwtProvider;
import com.javadev.demojwt.security.userPrincipal.UserPrincipal;
import com.javadev.demojwt.service.impl.RoleServiceImpl;
import com.javadev.demojwt.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@RequestMapping("api/auth")
@RestController
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private RoleServiceImpl roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtProvider jwtProvider;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody SignUpForm signUpForm) {
        if (userService.existsByUserName(signUpForm.getUserName())) {
            return new ResponseEntity<>(new ResponseMessage("The username is existed"), HttpStatus.OK);
        }
        if (userService.existsByEmail(signUpForm.getEmail())) {
            return new ResponseEntity<>(new ResponseMessage("The email is existed"), HttpStatus.OK);
        }

        User user = new User(signUpForm.getFirstName(), signUpForm.getLastName(), signUpForm.getUserName(), signUpForm.getEmail(), signUpForm.getPhone(), passwordEncoder.encode(signUpForm.getPassword()));
        Set<String> strRoles = signUpForm.getRoles();
        Set<Role> roles =  new HashSet<>();
        strRoles.forEach(role -> {
            switch (role) {
                case "admin":
                    Role adminRole = roleService.findByName(RoleName.ADMIN).orElseThrow(() -> new RuntimeException("Role not found"));
                    roles.add(adminRole);
                    break;

                case "author":
                    Role authorRole = roleService.findByName(RoleName.AUTHOR).orElseThrow(() -> new RuntimeException("Role not found"));
                    roles.add(authorRole);
                    break;

                default:
                    Role userRole = roleService.findByName(RoleName.USER).orElseThrow(() -> new RuntimeException("Role not found"));
                    roles.add(userRole);
                    break;
            }
        });
        user.setRoles(roles);
        userService.Save(user);

        return new ResponseEntity<>(new ResponseMessage("create successful!"), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody SignInForm signInForm) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInForm.getUserName(), signInForm.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.createToken(authentication);
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(token , userPrincipal.getFirstName(), userPrincipal.getAuthorities()));
    }

}
