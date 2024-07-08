package org.example;

import org.example.dto.UserNoteDTO;
import org.example.entity.User;
import org.example.entity.UserNote;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.example.repository.UserNoteRepository;
import org.example.repository.UserRepository;
import org.example.service.UserNoteService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserNoteServiceTest {

    @Mock
    private UserNoteRepository userNoteRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserNoteService userNoteService;

    private ModelMapper modelMapper;


    @BeforeEach
    public void setUp() {
        modelMapper = new ModelMapper();
        userNoteService = new UserNoteService(userNoteRepository, userRepository, modelMapper);
    }

    public User mockUser() {
        User user = new User();
        user.setId("1L");
        user.setUsername("ekin");
        user.setPassword("ekin123");
        user.setEmail("ekin@gmail.com");
        user.setPhoneNumber("05555555");
        return user;
    }

    public UserNote mockUserNotes (String id, String title, String description, User user) {
        UserNote userNote = new UserNote();
        userNote.setId(id);
        userNote.setTitle(title);
        userNote.setDescription(description);
        userNote.setUser(user);
        return userNote;
    }

    @Test
    public void testFindAllUserNotes() {

        User user = this.mockUser();
        UserNote userNote1  = this.mockUserNotes("1L", "Title 1", "Description 1", user);
        UserNote userNote2  = this.mockUserNotes("2L", "Title 2", "Description 2", user);

        when(userNoteRepository.findAll()).thenReturn(Arrays.asList(userNote1, userNote2));

        List<UserNoteDTO> result = userNoteService.findAll();

        assertEquals(2, result.size());

    }

    @Test
    public void testCreateUserNote() {

        User user = this.mockUser();
        UserNote userNote  = this.mockUserNotes("1L", "Title 1", "Description 1", user);

        when(userRepository.findById("1L")).thenReturn(Optional.of(user));
        when(userNoteRepository.save(any(UserNote.class))).thenReturn(userNote);

        UserNoteDTO userNoteDTO = modelMapper.map(userNote, UserNoteDTO.class);

        UserNoteDTO result = userNoteService.create(userNoteDTO);

        assertNotNull(result);
        verify(userRepository, times(1)).findById("1L");
        verify(userNoteRepository, times(1)).save(any(UserNote.class));

    }

    @Test
    public void testUpdateUserNote() {

        User user = this.mockUser();
        UserNote existingNote  = this.mockUserNotes("1L", "Title 1", "Description 1", user);
        UserNote updatedNote  = this.mockUserNotes("1L", "Change Title 2", "Description 1", user);

        UserNoteDTO newNoteDTO = modelMapper.map(updatedNote, UserNoteDTO.class);


        when(userNoteRepository.findById("1L")).thenReturn(Optional.of(existingNote));
        when(userNoteRepository.save(any(UserNote.class))).thenReturn(updatedNote);

        UserNoteDTO result = userNoteService.update("1L", newNoteDTO);

        assertNotNull(result);
        assertEquals("Change Title 2", result.getTitle());
        assertEquals("Description 1", result.getDescription());

        verify(userNoteRepository, times(1)).findById("1L");
        verify(userNoteRepository, times(1)).save(any(UserNote.class));
    }

    @Test

    public void testDeleteUSerNote() {

        User user = this.mockUser();
        UserNote userNote  = this.mockUserNotes("1L", "Title 1", "Description 1", user);

        when(userNoteRepository.findById("1L")).thenReturn(Optional.of(userNote));
        userNoteService.delete("1L");
        verify(userNoteRepository, times(1)).findById("1L");
        verify(userNoteRepository, times(1)).delete(userNote);
    }



}
