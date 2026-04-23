package org.example.springbootnotesassignment.repository;

import org.example.springbootnotesassignment.model.AppUser;
import org.example.springbootnotesassignment.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note,Long> {
    List<Note> findByUser(AppUser user);
    List<Note> findByUserId(Long id);


}
