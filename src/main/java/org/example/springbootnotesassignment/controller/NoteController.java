package org.example.springbootnotesassignment.controller;

import jakarta.servlet.http.HttpSession;
import org.example.springbootnotesassignment.model.AppUser;
import org.example.springbootnotesassignment.model.Note;
import org.example.springbootnotesassignment.repository.AppUserRepository;
import org.example.springbootnotesassignment.repository.NoteRepository;
import org.springframework.ui.Model;
import org.example.springbootnotesassignment.service.NoteService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class NoteController {
    private final AppUserRepository appUserRepository;

    NoteService noteService;
    public NoteController(NoteService noteService, AppUserRepository appUserRepository) {
        this.noteService = noteService;
        this.appUserRepository = appUserRepository;

    }
    @GetMapping("/notes")
    public String notes(Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        model.addAttribute("notes", noteService.getNotesByUsername(username));
        model.addAttribute("username", username);
        return "notes";
    }

    @GetMapping("/shownotes")
    public String getNotesByUsername(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");
        System.out.println("Username from session: " + username);
        AppUser user = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("username not found"));
        List<Note> notes = noteService.getNotesByUser(user);
        System.out.println("Notes " + notes);
        model.addAttribute("notes", noteService.getNotesByUser(user));
        model.addAttribute("username", username);
        return "shownotes";
    }
    @GetMapping("/editnote")
    public String editNote(@RequestParam Long id, HttpSession session, Model model) {
        System.out.println("Username from session: " + session.getAttribute("username" + session.getId()));
        String username = (String) session.getAttribute("username");
        System.out.println("Username from session: " + username);
        model.addAttribute("note", noteService.getNotesById(id));
        return "editnote";
    }
   /* @GetMapping("/adminpage")
    public String showAllNotes(Model model){
        model.addAttribute("notes",noteService.getAllNotes());

        return "adminpage";
    }*/
    @GetMapping("/delete")
    public String deleteNote(@RequestParam Long id, HttpSession session, Model model) {
        System.out.println("Username from session: " + session.getAttribute("username" + session.getId()));
        String username = (String) session.getAttribute("username");
        System.out.println("Username from session: " + username);
        noteService.deleteNote(id);
        return "redirect:/shownotes";

    }
    @PostMapping("/notes")
    public String addNote(@RequestParam String title,
                          @RequestParam String content,
                          HttpSession session
                         ) {
        String username = (String) session.getAttribute("username");
        noteService.addNote(username, title, content);
        return "redirect:/success";
    }
    /*@PostMapping("/adminpage")
    public String showAllNotesForAdmin(@RequestParam Long id, HttpSession session, Model model){
        model.addAttribute("notes",noteService.getAllNotes());

        return "adminpage";
    }*/
    @PostMapping("/editnote")
    public String editNotes(@RequestParam String title,
                            @RequestParam String content,
                            @RequestParam Long id
                           ){
                noteService.updateNote(id, title, content);
        return "redirect:/success";

    }
    @PostMapping("/delete")
    public String deleteNotes(@RequestParam Long id){
        noteService.deleteNote(id);
        return "redirect:/success";
    }
}
