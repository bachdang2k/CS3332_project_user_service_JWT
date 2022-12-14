package com.javadev.demojwt.security.userPrincipal;

import com.javadev.demojwt.model.User;
import com.javadev.demojwt.repository.IUsersRepository;
import com.javadev.demojwt.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UserDetailService implements UserDetailsService {

    @Autowired
    private IUsersRepository usersRepository;

    @Autowired
    private UserServiceImpl userService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = usersRepository.findByUserName(username).orElseThrow(
                () -> new UsernameNotFoundException("User not found -> user name or password" + username));
        return UserPrincipal.build(user);
    }

//    //HAM LAY RA USER HIEN TAI DE THUC HIEN THAO TAC VOI DB
//    public User getCurrentUser(){
//        Optional<User> user;
//        String userName;
//        //Lay 1 object principal trong SecurityContexHolder
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        //So sanh obj voi Userdetails neu ma dung thi gan userName = principal.getUsername();
//        if(principal instanceof UserDetails){
//            userName = ((UserDetails) principal).getUsername();
//        } else {
//            //neu khong phai user hien tai thi userName = principal.toString();
//            userName = principal.toString();
//        }
//        //kiem tra neu userName ton tai trong DB thi gan user = ham tim kiem trong DB theo userName do
//        if(usersRepository.existsByUserName(userName)){
//            user = userService.findByUserName(userName);
//        } else {
//            //Neu chua ton tai thi tra ve 1 the hien cua lop User thong qua Optional.of
//            user = Optional.of(new User());
//            //set cho no 1 cai ten user an danh Day la truong hop ma tuong tac qua dang nhap kieu FB hay GG
//            user.get().setUserName("Anonymous");
//        }
//        return user.get();
//    }
}
