package com.jiayupu.staybooking.service;

import com.jiayupu.staybooking.exception.UserAlreadyExistException;
import com.jiayupu.staybooking.model.Authority;
import com.jiayupu.staybooking.model.User;
import com.jiayupu.staybooking.model.UserRole;
import com.jiayupu.staybooking.repository.AuthorityRepository;
import com.jiayupu.staybooking.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegisterService {
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private PasswordEncoder passwordEncoder;


    public RegisterService(UserRepository userRepository, AuthorityRepository authorityRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void add(User user, UserRole role) {
        if (userRepository.existsById(user.getUsername())) {
            throw new UserAlreadyExistException("User already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        userRepository.save(user);
        authorityRepository.save(new Authority(user.getUsername(), role.name()));
    }

}

