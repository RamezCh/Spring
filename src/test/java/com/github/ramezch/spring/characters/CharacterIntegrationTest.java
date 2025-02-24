package com.github.ramezch.spring.characters;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CharacterIntegrationTest {
    @Autowired
    private MockMvc mvc;

    @Test
    void getCharacters_returnEmptyList() throws Exception {
        // WHEN
        mvc.perform(get("/api/asterix/characters"))
                // THEN
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    @DirtiesContext
    void createCharacter() throws Exception {
        // WHEN
        mvc.perform(post("/api/asterix/characters")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                        "name": "alekhandro",
                        "age": 20,
                        "profession": "warrior"
                        }
                        """))
                // THEN
                .andExpect(status().isCreated())
                .andExpect(content().json("""
                        {
                        "name": "alekhandro",
                        "age": 20,
                        "profession": "warrior"
                        }
                        """));

    }

    @Test
    @DirtiesContext
    void getAverageAge() throws Exception {
        // GIVEN
        mvc.perform(post("/api/asterix/characters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                    "id": "123qwe",
                    "name": "alekhandro",
                    "age": 20,
                    "profession": "warrior"
                    }
                    """))
                .andExpect(status().isCreated());

        mvc.perform(post("/api/asterix/characters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                    "id": "456rty",
                    "name": "anotherCharacter",
                    "age": 30,
                    "profession": "mage"
                    }
                    """))
                .andExpect(status().isCreated());

        // WHEN
        mvc.perform(get("/api/asterix/characters/average-age"))
                // THEN
                .andExpect(status().isOk())
                .andExpect(content().string("25.0"));
    }
}