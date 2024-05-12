package com.luvannie.springbootbookecommerce.service;

import com.luvannie.springbootbookecommerce.dao.UserRepository;
import com.luvannie.springbootbookecommerce.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;
//    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    public User register(User user) {
        System.out.println("register: " + user.getUsername() + " " + user.getAccount() + " " + user.getPassword() + " " + user.getPhoneNumber() + " " + user.getEmail() );
        user.setUsername(user.getUsername());
        user.setAccount(user.getAccount());
        user.setPassword(user.getPassword());
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setPhoneNumber(user.getPhoneNumber());
        user.setEmail(user.getEmail());
        return userRepository.save(user);
    }

    public User login(String account, String password) {
        User user = userRepository.findByAccount(account);
//        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
//            return user;
//        }
        if(user != null && password.equals(user.getPassword())){
            return user;
        }
        return null;
    }


}