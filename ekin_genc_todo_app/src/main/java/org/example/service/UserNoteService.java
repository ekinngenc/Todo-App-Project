package org.example.service;

import org.example.dto.UserNoteDTO;
import org.example.entity.User;
import org.example.repository.UserRepository;
import org.example.entity.UserNote;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.example.repository.UserNoteRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserNoteService {

    private final UserNoteRepository userNoteRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public List<UserNoteDTO> findAll() {
        List<UserNote> userNotes = this.userNoteRepository.findAll();
        return userNotes.stream()
                .map(userNote -> modelMapper.map(userNote, UserNoteDTO.class))
                .collect(Collectors.toList());
    }

    public UserNoteDTO create(UserNoteDTO userNoteDTO) {
        UserNote userNote = modelMapper.map(userNoteDTO, UserNote.class);
        User user = this.userRepository.findById(userNote.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        userNote.setUser(user);
        return this.modelMapper.map(this.userNoteRepository.save(userNote),  UserNoteDTO.class);
    }

    public UserNoteDTO update(String id, UserNoteDTO userNoteDTO) {
        UserNote userNote = userNoteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("UserNote not found"));
        userNote.setTitle(userNoteDTO.getTitle());
        userNote.setDescription(userNoteDTO.getDescription());
        return modelMapper.map(userNoteRepository.save(userNote), UserNoteDTO.class);
    }

    public void delete(String id) {
        UserNote userNote = userNoteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("UserNote not found"));
        userNoteRepository.delete(userNote);
    }
}
