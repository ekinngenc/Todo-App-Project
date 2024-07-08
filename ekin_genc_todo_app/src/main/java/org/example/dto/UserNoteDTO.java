package org.example.dto;

import lombok.Data;

@Data
public class UserNoteDTO {

    private String id;
    private String title;
    private String description;
    private UserDTO user;

}
