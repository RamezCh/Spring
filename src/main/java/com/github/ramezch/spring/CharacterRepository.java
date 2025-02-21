package com.github.ramezch.spring;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CharacterRepository extends MongoRepository<Character, String> {
    List<Character> findByNameContainingIgnoreCase(String name);
    List<Character> findByAge(Integer age);
    List<Character> findByProfessionContainingIgnoreCase(String profession);
    List<Character> findByAgeLessThanEqual(int maxAge);
}
