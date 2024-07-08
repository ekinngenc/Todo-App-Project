package org.example;

import org.example.controller.AuthController;
import org.example.dto.AuthResponseDTO;
import org.example.dto.LoginDTO;
import org.example.dto.UserDTO;
import org.example.helper.JWTGenerator;
import org.example.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserService userService;

    @Mock
    private JWTGenerator jwtGenerator;

    @InjectMocks
    private AuthController authController;

    public UserDTO mockUser() {
        UserDTO user = new UserDTO();
        user.setId("1L");
        user.setUsername("ekin");
        user.setPassword("ekin123");
        user.setEmail("ekin@gmail.com");
        user.setPhoneNumber("05555555");
        return user;
    }

    @Test
    public void testRegister() {

        UserDTO userDTO = this.mockUser();
        UserDTO responseDTO = this.mockUser();

        when(userService.registerUser(any(UserDTO.class))).thenReturn(responseDTO);

        UserDTO result = authController.register(userDTO).getBody();

        assertNotNull(result);
        assertEquals("ekin", result.getUsername());
        assertEquals("ekin123", result.getPassword());
        assertEquals("ekin@gmail.com", result.getEmail());
        assertEquals("05555555", result.getPhoneNumber());

    }

    @Test
    public void testLogin() {

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername("ekin");
        loginDTO.setPassword("ekin123");
        String token = "mocked-jwt-token";
        Authentication authentication = mock(Authentication.class);

        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(jwtGenerator.generateToken(any(Authentication.class))).thenReturn(token);

        AuthResponseDTO response = authController.login(loginDTO).getBody();

        assertNotNull(response);
        assertEquals(token, response.getAccessToken());
    }

}
