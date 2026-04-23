package org.example.springbootnotesassignment.service;

import org.example.springbootnotesassignment.model.AppUser;
import org.example.springbootnotesassignment.model.Note;
import org.example.springbootnotesassignment.repository.AppUserRepository;
import org.example.springbootnotesassignment.repository.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

    private final NoteRepository noteRepository;
    private final AppUserRepository appUserRepository;
    public NoteService(NoteRepository noteRepository, AppUserRepository appUserRepository) {
        this.noteRepository = noteRepository;
        this.appUserRepository =  appUserRepository;

    }
    public void addNote(String username, String title, String content) {
        AppUser appUser = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
        Note note = new Note();
        note.setTitle(title);
        note.setContent(content);
        note.setUser(appUser);
        noteRepository.save(note);

    }
    public void updateNote(Long id, String title, String content) {
        Note note = getNotesById(id);
        note.setTitle(title);
        note.setContent(content);
        noteRepository.save(note);

    }
    public void deleteNote(Long id) {
        Note note = getNotesById(id);
        noteRepository.delete(note);
    }

    public List<Note> getNotesByUsername(String username) {
        AppUser appUser = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
        return noteRepository.findByUser(appUser);
    }
    public List<Note> getNotesByUser(AppUser user) {
        return noteRepository.findByUser(user);
    }
    public Note getNotesById(Long id) {
        return noteRepository.findById(id).orElseThrow(() -> new RuntimeException("Note not found: "));
    }




}
