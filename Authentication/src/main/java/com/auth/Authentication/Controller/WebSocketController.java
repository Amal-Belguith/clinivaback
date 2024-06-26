package com.auth.Authentication.Controller;

import com.auth.Authentication.User.ChatMessage;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

@Controller
public class WebSocketController {

    private ConcurrentMap<String, ConcurrentMap<String, ChatMessage>> rooms = new ConcurrentHashMap<>();

    @MessageMapping("/chat/{roomId}")
    @SendTo("/topic/{roomId}")
    public ChatMessage chat(@DestinationVariable String roomId, ChatMessage message) {
        System.out.println(message);
        return new ChatMessage(message.getMessage(), message.getUser(), message.getFirstName(), message.getLastName());
    }

// méthode gère la récupération de la liste des utilisateurs dans une salle de chat spécifique lorsqu'un client s'abonne au topic des utilisateurs.
    @SubscribeMapping("/users/{roomId}")
    @SendTo("/topic/users/{roomId}")
    public List<ChatMessage> getUsers(@DestinationVariable String roomId) {
        return rooms.getOrDefault(roomId, new ConcurrentHashMap<>())
                .values()
                .stream()
                .collect(Collectors.toList());
    }

    @MessageMapping("/join/{roomId}")
    @SendTo("/topic/users/{roomId}")
    public List<ChatMessage> join(@DestinationVariable String roomId, ChatMessage message) {
        System.out.println("User joined: " + message.getUser());
        rooms.putIfAbsent(roomId, new ConcurrentHashMap<>());
        rooms.get(roomId).put(message.getUser(), message);
        return getUsers(roomId);
    }

    @MessageMapping("/leave/{roomId}")
    @SendTo("/topic/users/{roomId}")
    public List<ChatMessage> leave(@DestinationVariable String roomId, ChatMessage message) {
        System.out.println("User left: " + message.getUser());
        if (rooms.containsKey(roomId)) {
            rooms.get(roomId).remove(message.getUser());
        }
        return getUsers(roomId);
    }
}