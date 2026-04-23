package org.example.springbootnotesassignment.service;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.example.springbootnotesassignment.model.AppUser;
import org.example.springbootnotesassignment.repository.AppUserRepository;
@Service
public class RegisterService {
    private final AppUserRepository repository;

    // DONT CREATE A NEW OBJECT HERE fixet var att använda sig av självast @Bean namnet.
   private final PasswordEncoder encoder;

    // AFTER BCRYPT WORKS ADD TO CONSTRUCTOR. och lägg in @Bean encoder variablen här.
    public RegisterService(AppUserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    public boolean registerUser(String username, String password) {
        String hashedPassword = encoder.encode(password);


        if (username == null || username.isBlank()) {
            return false;
        }
        if (hashedPassword == null || password.isBlank()) {
            return false;
        }
        if(repository.findByUsername(username).isPresent()) {
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