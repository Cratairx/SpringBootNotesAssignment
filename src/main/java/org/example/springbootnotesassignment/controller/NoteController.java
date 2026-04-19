package org.example.springbootnotesassignment.controller;

import org.example.springbootnotesassignment.service.NoteService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NoteController {
    NoteService noteService;
    @PostMapping("/notes")
    public String addNote(@RequestParam String title,
                          @RequestParam String content,
                          @RequestParam String username){
        noteService.addNote(username, title, content);
        return "redirect:/notes";
    }
}
