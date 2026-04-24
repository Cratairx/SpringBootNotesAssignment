package org.example.springbootnotesassignment.controller;

import jakarta.servlet.http.HttpSession;
import org.example.springbootnotesassignment.model.AppUser;
import org.example.springbootnotesassignment.repository.AppUserRepository;
import org.example.springbootnotesassignment.service.LogInService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.example.springbootnotesassignment.service.RegisterService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;


@Controller
public class RegisterController {

    private final RegisterService service;
    private final LogInService logInService;
    private final AppUserRepository appUserRepository;
    private String role;

    public RegisterController(RegisterService service, LogInService logInService, AppUserRepository appUserRepository) {
        this.service = service;
        this.logInService = logInService;
        this.appUserRepository = appUserRepository;
    }


    @GetMapping("/register")
    public String register(HttpSession session, Model model) {
        model.addAttribute("username",session.getAttribute("username"));
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username,
                               @RequestParam String password,
                               HttpSession session,
                               Model model){

        boolean result = service.registerUser(username,password);
        if(result){
            session.setAttribute("username", username);
            return "redirect:/success";
        }
        model.addAttribute("error", "could not register user");

        return "register";
    }
    @GetMapping("/adminpage")
    public String adminSuccess(HttpSession session, Model model){
        model.addAttribute("username",session.getAttribute("username"));
        return "adminpage";
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
                 return "redirect:/adminpage";
             }
             return "redirect:/success";
         }

         redirectAttributes.addFlashAttribute("error", "could not login, username or password is incorrect.");

         return "login";
     }


    @GetMapping("/success")
    public String success(HttpSession session, Model model){
        model.addAttribute("username",session.getAttribute("username"));
        return "success";
    }

   @GetMapping("/")
    public String index(){
        return "redirect:/login";
    }
    @GetMapping("/login")
    public String login(){
        return "login";
    }
}