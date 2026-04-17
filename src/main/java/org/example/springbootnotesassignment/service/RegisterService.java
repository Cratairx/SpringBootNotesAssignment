package org.example.springbootnotesassignment.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.example.springbootnotesassignment.model.AppUser;
import org.example.springbootnotesassignment.repository.AppUserRepository;
@Service
public class RegisterService {
    private final AppUserRepository repository;

    // DONT CREATE A NEW OBJECT HERE
   private final BCryptPasswordEncoder encoder =  new BCryptPasswordEncoder();

    // AFTER BCRYPT WORKS ADD TO CONSTRUCTOR
    public RegisterService(AppUserRepository repository) {
        this.repository = repository;

    }

    public boolean registerUser(String username, String password) {
        String hashedPassword = encoder.encode(password);


        if (username == null || username.isBlank()) {
            return false;
        }
        if (hashedPassword == null || password.isBlank()) {
            return false;
        }
        AppUser user = new AppUser();
        user.setUsername(username);


        user.setPassword(hashedPassword);
        user.setRole("USER");
        repository.save(user);
        return true;
    }

}