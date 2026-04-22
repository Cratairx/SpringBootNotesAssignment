package org.example.springbootnotesassignment.controller;

import org.example.springbootnotesassignment.model.Note;
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
    NoteService noteService;
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }
    @GetMapping("/notes")
    public String notes(Model model, @RequestParam(required = false) String username) {
        model.addAttribute("notes", noteService.getNotesByUsername(username));
        model.addAttribute("username", username);
        return "notes";
    }
    /*@GetMapping("/notes")
    public List<Note> getNotesByUsername(@RequestParam(required = false) Long userId) {
        return noteService.getNotesById(userId);
    }*/
    @PostMapping("/notes")
    public String addNote(@RequestParam String title,
                          @RequestParam String content,
                         Principal principal,
                         // @RequestParam String username,
                          RedirectAttributes redirectAttributes) {
        String username = principal.getName();
        noteService.addNote(username, title, content);
        redirectAttributes.addFlashAttribute("username",username);
        return "redirect:/success";
    }


}
