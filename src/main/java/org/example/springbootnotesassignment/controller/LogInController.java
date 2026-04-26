package org.example.springbootnotesassignment.controller;

import jakarta.servlet.http.HttpSession;
import org.example.springbootnotesassignment.model.AppUser;
import org.example.springbootnotesassignment.repository.AppUserRepository;
import org.example.springbootnotesassignment.service.LogInService;
import org.example.springbootnotesassignment.service.RegisterService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller

public class LogInController {
    private final RegisterService service;
    private final LogInService logInService;
    private final AppUserRepository appUserRepository;

    public LogInController(RegisterService service, LogInService logInService, AppUserRepository appUserRepository) {
        this.service = service;
        this.logInService = logInService;
        this.appUserRepository = appUserRepository;
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }
    @PostMapping("/login")
    public String loginUser(@RequestParam String username,
                            @RequestParam String password,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {

        boolean result = logInService.logIn(username, password);
        AppUser appUser = null;
        if (result) {
            appUser = appUserRepository.findByUsername(username).get();
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
