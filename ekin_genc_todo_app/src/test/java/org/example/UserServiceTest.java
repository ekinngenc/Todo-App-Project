package org.example;

import org.example.dto.UserDTO;
import org.example.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.example.repository.UserRepository;
import org.example.service.UserService;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private ModelMapper modelMapper;


    @BeforeEach
    public void setUp() {
        modelMapper = new ModelMapper();
        userService = new UserService(userRepository, modelMapper, passwordEncoder);
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

    @Test
    public void testRegisterUser() {
        User user = this.mockUser();
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        UserDTO savedUser = userService.registerUser(userDTO);

        assertNotNull(savedUser);
        verify(passwordEncoder, times(1)).encode(anyString());
        verify(userRepository, times(1)).save(any(User.class));
    }
}
