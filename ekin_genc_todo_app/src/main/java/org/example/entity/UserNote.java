package org.example.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;

@Document
@Getter
@Setter
public class UserNote {

    @Id
    private String id;
    private String title;
    private String description;
    private User user;
}
