package org.example.springbootnotesassignment.controller;

import jakarta.servlet.http.HttpSession;
import org.example.springbootnotesassignment.model.AppUser;
import org.example.springbootnotesassignment.repository.AppUserRepository;
import org.example.springbootnotesassignment.repository.NoteRepository;
import org.example.springbootnotesassignment.service.NoteService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {
    private final AppUserRepository appUserRepository;
    private final NoteRepository noteRepository;
  private final  NoteService noteService;
    private final PasswordEncoder passwordEncoder;

    public AdminController(AppUserRepository appUserRepository, NoteRepository noteRepository, NoteService noteService, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.noteRepository = noteRepository;
        this.noteService = noteService;
        this.passwordEncoder = passwordEncoder;
    }

   /* @GetMapping("/adminpage")
    public String showAllNotes(Model model, HttpSession session){
        //model.addAttribute("notes",noteService.getAllNotes());
        String username = (String) session.getAttribute("username");
       session.setAttribute("username", username);
        return "adminpage";
    }*/
   /*@PostMapping("/adminpage")
   public String showAllNotesForAdmin(@RequestParam Long id, HttpSession session, Model model){
       String username = (String) session.getAttribute("username");
       System.out.println("Username ska vara här: " + username);
       model.addAttribute("notes",noteService.getAllNotes());
       session.setAttribute("username", username);
       return "adminpage";
   }*/

    @GetMapping("/adminpage")
    public String adminPage(Model model, HttpSession session){

        String username = (String) session.getAttribute("username");
        String role = (String) session.getAttribute("role");
        if (!"ADMIN".equals(role)) {
            return "redirect:/login";
        }
        model.addAttribute("username", username);
        model.addAttribute("notes",noteService.getAllNotes());
        return "adminpage";

      //  session.setAttribute("username", username);

    }
     @PostMapping("/adminpage")
    public String adminPage(@RequestParam Long id, HttpSession session, Model model){
         String username = (String) session.getAttribute("username");
         model.addAttribute("username", username);
         return "adminpage";
    }
    @GetMapping("/showUserNotes")
    public String showUserNotes(Model model, HttpSession session){

        String username = (String) session.getAttribute("username");
        model.addAttribute("username", username);
        model.addAttribute("notes",noteService.getAllNotes());
        return "showUserNotes";
    }
    @PostMapping("/showUserNotes")
    public String showUserNotes(@RequestParam Long id, HttpSession session, Model model){
        String username = (String) session.getAttribute("username");
        return "showUserNotes";
    }
    @GetMapping("/adminnotes")
    public String notes(Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        model.addAttribute("notes", noteService.getNotesByUsername(username));
        model.addAttribute("username", username);
        return "adminnotes";
    }
    // sdasdasd asd
    @PostMapping("/adminnotes")
    public String addNote(@RequestParam String title,
                          @RequestParam String content,
                          HttpSession session
    ) {
        String username = (String) session.getAttribute("username");
        noteService.addNote(username, title, content);
        return "redirect:/adminpage";
    }

    @GetMapping("/adminchangepassword")
    public String changePassword(HttpSession session, Model model) {
        model.addAttribute("username",session.getAttribute("username"));
        return "/adminchangepassword";
    }
    @PostMapping("/adminchangepassword")
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
}
