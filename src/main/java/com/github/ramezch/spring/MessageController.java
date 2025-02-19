package com.github.ramezch.spring;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    private final Map<String, Message> messages = new HashMap<>();

    @GetMapping("")
    public Map<String, Message> getMessages() {
        return messages;
    }

    @PostMapping("")
    public Message createMessage(@RequestBody Message newMessage) {
        messages.put(newMessage.getId(), newMessage);
        return newMessage;
    }

    @DeleteMapping("/{id}")
    public String deleteMessage(@PathVariable String id) {
        if (messages.containsKey(id)) {
            messages.remove(id);
            return "Message with id " + id + " deleted successfully.";
        }
        return "Message with id " + id + " not found.";
    }

}
