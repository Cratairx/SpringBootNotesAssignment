package org.example.springbootnotesassignment.controller;

import jakarta.servlet.http.HttpSession;
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

    public RegisterController(RegisterService service, LogInService logInService) {
        this.service = service;
        this.logInService = logInService;
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
     @PostMapping("/login")
    public String loginUser(@RequestParam String username,
                               @RequestParam String password,
                               HttpSession session,
                               RedirectAttributes redirectAttributes){

        boolean result = logInService.logIn(username,password);
        if(result){
            System.out.println("username" + username);
            session.setAttribute("username", username);
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