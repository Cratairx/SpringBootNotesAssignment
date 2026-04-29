package org.example.springbootnotesassignment.controller;

import jakarta.servlet.http.HttpSession;
import org.example.springbootnotesassignment.model.AppUser;
import org.example.springbootnotesassignment.repository.AppUserRepository;
import org.example.springbootnotesassignment.service.LogInService;
import org.example.springbootnotesassignment.service.RegisterService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller

public class LogInController {
    private final RegisterService service;
    private final LogInService logInService;
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    public LogInController(RegisterService service, LogInService logInService, AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        this.service = service;
        this.logInService = logInService;
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @GetMapping("/changepassword")
    public String changePassword(HttpSession session, Model model) {
        model.addAttribute("username",session.getAttribute("username"));
        return "changepassword";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }
    @PostMapping("/changepassword")
    public String changePassword( HttpSession session, @RequestParam String oldPassword, @RequestParam String newPassword){
        System.out.println("changed password reached");
        String username = session.getAttribute("username").toString();
        AppUser appUser = appUserRepository.findByUsername(username).get();
        oldPassword = oldPassword.trim();
        newPassword = newPassword.trim();
        if(passwordEncoder.matches(oldPassword,appUser.getPassword())){
           appUser.setPassword(passwordEncoder.encode(newPassword));
           appUserRepository.save(appUser);
        }
        return "redirect:/login";
    }
    @PostMapping("/login")
    public String loginUser(@RequestParam String username,
                            @RequestParam String password,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {

        boolean result = logInService.logIn(username, password);

        if (result) {
            AppUser  appUser = appUserRepository.findByUsername(username).get();
            session.setAttribute("role", appUser.getRole());
            System.out.println("Här ska det stå admin " + appUser.getRole());
            session.setAttribute("username", username);
            if("ADMIN".equals(appUser.getRole())){
                System.out.println("Kommer vi hit?");
                System.out.println(username);
                return "redirect:/adminpage";
            }
            return "redirect:/success";
        }

        redirectAttributes.addFlashAttribute("error", "could not login, username or password is incorrect.");

        return "login";
    }


}
