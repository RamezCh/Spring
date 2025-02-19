package com.github.ramezch.spring;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Message {
    private String name, message, id;

    public Message(String name, String message, String id) {
        this.name = name;
        this.message = message;
        this.id = id;
    }

}
