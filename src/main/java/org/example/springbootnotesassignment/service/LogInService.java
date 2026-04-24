package org.example.springbootnotesassignment.service;

import org.example.springbootnotesassignment.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
@Service
public class LogInService {

@Autowired
private AppUserRepository appUserRepository;
private PasswordEncoder encoder;

public LogInService(AppUserRepository appUserRepository, PasswordEncoder encoder) {
    this.appUserRepository = appUserRepository;
    this.encoder = encoder;

}
    public boolean logIn(String username, String password) {
        if (username == null || password == null) {
            return false;
        }
        if(appUserRepository.findByUsername(username).isPresent()){
            return encoder.matches(password, appUserRepository.findByUsername(username)
                    .get().getPassword());


        }

        return false;

    }

    }
