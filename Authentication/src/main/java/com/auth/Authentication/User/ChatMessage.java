package com.auth.Authentication.User;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatMessage {
    String message;
    String user;
    private String firstName;
    private String lastName;
}
