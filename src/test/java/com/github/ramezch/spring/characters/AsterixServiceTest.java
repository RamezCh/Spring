package com.github.ramezch.spring.characters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AsterixServiceTest {

    private CharacterRepository mockCharacterRepo;
    private IdService mockIdService;
    private AsterixService characterService;

    private Character testChar;
    private Character me;

    @BeforeEach
    void setUp() {
        // Initialize mocks
        mockCharacterRepo = mock(CharacterRepository.class);
        mockIdService = mock(IdService.class);

        // Initialize service with mocks
        characterService = new AsterixService(mockCharacterRepo, mockIdService);

        // Test data
        testChar = new Character("1", "alex", 25, "java dev");
        me = new Character("2", "Ramez", 25, "Software Engineer");
    }

    @Test
    void getCharacters_ReturnsAllCharacters_WhenRepositoryIsNotEmpty() {
        // GIVEN
        when(mockCharacterRepo.findAll()).thenReturn(List.of(testChar, me));

        // WHEN
        List<Character> actual = characterService.getCharacters(null, null, null, null);

        // THEN
        verify(mockCharacterRepo).findAll();
        assertIterableEquals(List.of(testChar, me), actual);
    }

    @Test
    void getCharacters_ReturnsEmptyList_WhenRepositoryIsEmpty() {
        // GIVEN
        when(mockCharacterRepo.findAll()).thenReturn(List.of());

        // WHEN
        List<Character> actual = characterService.getCharacters(null, null, null, null);

        // THEN
        verify(mockCharacterRepo).findAll();
        assertTrue(actual.isEmpty());
    }

    @Test
    void updateCharacter_ReturnsUpdatedCharacter_WhenCharacterExists() {
        // GIVEN
        CharacterDTO testCharDTO = new CharacterDTO("Ramez", 25, "Software Engineer");
        Character updatedCharacter = new Character("1", "Ramez", 25, "Software Engineer");

        when(mockCharacterRepo.findById("1")).thenReturn(Optional.of(testChar));
        when(mockCharacterRepo.save(updatedCharacter)).thenReturn(updatedCharacter);

        // WHEN
        Character actual = characterService.updateCharacter("1", testCharDTO);

        // THEN
        verify(mockCharacterRepo).findById("1");
        verify(mockCharacterRepo).save(updatedCharacter);
        assertEquals(updatedCharacter, actual);
    }

    @Test
    void updateCharacter_ThrowsException_WhenCharacterNotFound() {
        // GIVEN
        CharacterDTO testCharDTO = new CharacterDTO("Ramez", 25, "Software Engineer");
        when(mockCharacterRepo.findById("1")).thenReturn(Optional.empty());

        // WHEN & THEN
        assertThrows(RuntimeException.class, () -> characterService.updateCharacter("1", testCharDTO));
        verify(mockCharacterRepo).findById("1");
        verify(mockCharacterRepo, never()).save(any());
    }

    @Test
    void deleteCharacter_DeletesCharacter_WhenCharacterExists() {
        // GIVEN
        String id = "1";
        when(mockCharacterRepo.existsById(id)).thenReturn(true);
        doNothing().when(mockCharacterRepo).deleteById(id);

        // WHEN
        characterService.deleteCharacter(id);

        // THEN
        verify(mockCharacterRepo).existsById(id);
        verify(mockCharacterRepo).deleteById(id);
    }

    @Test
    void deleteCharacter_ThrowsException_WhenCharacterNotFound() {
        // GIVEN
        String id = "1";
        when(mockCharacterRepo.existsById(id)).thenReturn(false);

        // WHEN & THEN
        assertThrows(RuntimeException.class, () -> characterService.deleteCharacter(id));
        verify(mockCharacterRepo).existsById(id);
        verify(mockCharacterRepo, never()).deleteById(id);
    }

    @Test
    void getCharacterById_ReturnsCharacter_WhenCharacterExists() {
        // GIVEN
        when(mockCharacterRepo.findById("1")).thenReturn(Optional.of(testChar));

        // WHEN
        Optional<Character> actual = characterService.getCharacterById("1");

        // THEN
        verify(mockCharacterRepo).findById("1");
        assertEquals(Optional.of(testChar), actual);
    }

    @Test
    void getCharacterById_ReturnsEmptyOptional_WhenCharacterNotFound() {
        // GIVEN
        when(mockCharacterRepo.findById("1")).thenReturn(Optional.empty());

        // WHEN
        Optional<Character> actual = characterService.getCharacterById("1");

        // THEN
        verify(mockCharacterRepo).findById("1");
        assertEquals(Optional.empty(), actual);
    }
}