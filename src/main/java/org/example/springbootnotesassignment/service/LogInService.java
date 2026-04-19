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
        if (username.equals("admin") && password.equals("admin")) {
            return true;
        }
        if (username.equals("user") && password.equals("user")) {
            return true;
        }
        return false; // detta ska ändra tar bara bort det röda så länge
    }
}

