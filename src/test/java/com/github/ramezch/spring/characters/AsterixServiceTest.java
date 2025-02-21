package com.github.ramezch.spring.characters;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AsterixServiceTest {

    private final CharacterRepository mockCharacterRepo = mock(CharacterRepository.class);
    IdService mockIdService = mock(IdService.class);
    AsterixService characterService = new AsterixService(mockCharacterRepo, mockIdService);
    @Test
    void getCharacters_WhenCalled_ReturnAllCharacters() {
        // GIVEN
        com.github.ramezch.spring.characters.Character testChar = new com.github.ramezch.spring.characters.Character("1", "alex", 25, "java dev");
        com.github.ramezch.spring.characters.Character me = new com.github.ramezch.spring.characters.Character("2", "Ramez", 25, "Software Engineer");
        when(mockCharacterRepo.findAll()).thenReturn(List.of(testChar, me));
        // WHEN
        List<com.github.ramezch.spring.characters.Character> actual = characterService.getCharacters(null, null, null, null);

        // THEN
        verify(mockCharacterRepo).findAll();
        List<Character> expected = List.of(testChar, me);
        assertEquals(expected, actual);
    }

    @Test
    void updateCharacter() {
    }

    @Test
    void deleteCharacter() {
    }

    @Test
    void getCharacterById() {
    }
}