package com.github.ramezch.spring.characters;

import lombok.With;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("characters")
@With
public record Character(@Id String id, String name, int age, String profession) {
}
