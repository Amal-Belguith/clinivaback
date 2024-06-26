package com.chatting.chat.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepo extends JpaRepository<User,String> {
    List<User> findAllByStatus(Status status);
}
