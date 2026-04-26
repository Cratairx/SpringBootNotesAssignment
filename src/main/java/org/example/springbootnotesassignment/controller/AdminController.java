package org.example.springbootnotesassignment.controller;

import jakarta.servlet.http.HttpSession;
import org.example.springbootnotesassignment.repository.AppUserRepository;
import org.example.springbootnotesassignment.repository.NoteRepository;
import org.example.springbootnotesassignment.service.NoteService;
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

    public AdminController(AppUserRepository appUserRepository, NoteRepository noteRepository, NoteService noteService) {
        this.appUserRepository = appUserRepository;
        this.noteRepository = noteRepository;
        this.noteService = noteService;
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
        //model.addAttribute("notes",noteService.getAllNotes());
        String username = (String) session.getAttribute("username");
        model.addAttribute("username", username);

        session.setAttribute("username", username);
        return "adminpage";
    }
     @PostMapping("/adminpage")
    public String adminPage(@RequestParam Long id, HttpSession session, Model model){
         String username = (String) session.getAttribute("username");
         System.out.println("Username ska vara här: " + username);
      //  model.addAttribute("notes",noteService.getAllNotes());
         model.addAttribute("username", username);
        return "adminpage";
    }
    @GetMapping("/showUserNotes")
    public String showUserNotes(Model model, HttpSession session){
        //model.addAttribute("notes",noteService.getAllNotes());
        String username = (String) session.getAttribute("username");
        model.addAttribute("username", username);

        session.setAttribute("username", username);
        return "showUserNotes";
    }
    @PostMapping("/showUserNotes")
    public String showUserNotes(@RequestParam Long id, HttpSession session, Model model){
        String username = (String) session.getAttribute("username");
         model.addAttribute("notes",noteService.getAllNotes());
        model.addAttribute("username", username);
        return "showUserNotes";
    }
}
