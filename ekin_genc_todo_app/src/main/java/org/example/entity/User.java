package org.example.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;

@Document
@Getter
@Setter
public class User {

    @Id
    private String id;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;

}
