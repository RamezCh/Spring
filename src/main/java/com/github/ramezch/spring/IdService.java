package com.github.ramezch.spring;

import org.springframework.stereotype.Component;

import java.util.UUID;
@Component
public class IdService {
    public String randomId() {
        return UUID.randomUUID().toString();
    }
}