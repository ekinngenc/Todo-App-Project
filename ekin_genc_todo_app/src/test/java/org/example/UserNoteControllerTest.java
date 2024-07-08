package org.example;

import org.example.controller.UserNoteController;
import org.example.dto.UserDTO;
import org.example.dto.UserNoteDTO;
import org.example.service.UserNoteService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;

import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserNoteControllerTest {

    @Mock
    private UserNoteService userNoteService;

    @InjectMocks
    private UserNoteController userNoteController;

    public UserDTO mockUser() {
        UserDTO user = new UserDTO();
        user.setId("1L");
        user.setUsername("ekin");
        user.setPassword("ekin123");
        user.setEmail("ekin@gmail.com");
        user.setPhoneNumber("05555555");
        return user;
    }

    public UserNoteDTO mockUserNoteDTO () {
        UserNoteDTO userNoteDTO = new UserNoteDTO();
        userNoteDTO.setId("1L");
        userNoteDTO.setTitle("Title");
        userNoteDTO.setDescription("Description");
        userNoteDTO.setUser(this.mockUser());
        return userNoteDTO;
    }

    @Test
    public void testFindAll() {
        UserNoteDTO userNoteDTO = this.mockUserNoteDTO();
        when(userNoteService.findAll()).thenReturn(Collections.singletonList(userNoteDTO));
        userNoteController.findAll();
        verify(userNoteService, times(1)).findAll();
    }

    @Test
    public void testCreate() {
        UserNoteDTO userNoteDTO = this.mockUserNoteDTO();
        when(userNoteService.create(any(UserNoteDTO.class))).thenReturn(userNoteDTO);
        userNoteController.create(userNoteDTO);
        verify(userNoteService, times(1)).create(any(UserNoteDTO.class));
    }

    @Test
    public void testUpdate() {
        UserNoteDTO userNoteDTO = this.mockUserNoteDTO();
        when(userNoteService.update(anyString(), any(UserNoteDTO.class))).thenReturn(userNoteDTO);
        userNoteController.update("1L", userNoteDTO);
        verify(userNoteService, times(1)).update(anyString(), any(UserNoteDTO.class));
    }

    @Test
    public void testDelete() {
        doNothing().when(userNoteService).delete(anyString());
        userNoteController.delete("1L");
        verify(userNoteService, times(1)).delete(anyString());
    }
}
