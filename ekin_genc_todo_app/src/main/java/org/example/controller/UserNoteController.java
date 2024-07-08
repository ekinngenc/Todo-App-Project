package org.example.controller;


import org.example.dto.UserNoteDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.service.UserNoteService;

import java.util.List;


@RestController
@RequestMapping("/user/note")
@RequiredArgsConstructor
public class UserNoteController {

    private final UserNoteService userNoteService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserNoteDTO>> findAll() {
        return ResponseEntity.ok(this.userNoteService.findAll());
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserNoteDTO> create(@RequestBody UserNoteDTO userNoteDTO) {
        return ResponseEntity.ok(this.userNoteService.create(userNoteDTO));
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserNoteDTO> update(@RequestParam(name = "id") String id, @RequestBody UserNoteDTO userNoteDTO) {
        return ResponseEntity.ok(this.userNoteService.update(id, userNoteDTO));
    }

    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@RequestParam(name = "id") String id) {
        this.userNoteService.delete(id);
        return ResponseEntity.ok().build();
    }
}
