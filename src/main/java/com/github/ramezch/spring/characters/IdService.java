package com.github.ramezch.spring.characters;

import org.springframework.stereotype.Component;

import java.util.UUID;
@Component
public class IdService {
    public String randomId() {
        return UUID.randomUUID().toString();
    }
}