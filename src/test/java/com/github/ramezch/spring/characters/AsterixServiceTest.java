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
    void getCharacters_WhenNotEmpty_ShouldReturnAllCharacters() {
        // GIVEN
        when(mockCharacterRepo.findAll()).thenReturn(List.of(testChar, me));

        // WHEN
        List<Character> actual = characterService.getCharacters(null, null, null, null);

        // THEN
        verify(mockCharacterRepo).findAll();
        List<Character> expected = List.of(testChar, me);
        assertEquals(expected, actual);
    }

    @Test
    void getCharacters_WhenEmpty_ShouldReturnEmptyList() {
        // GIVEN
        when(mockCharacterRepo.findAll()).thenReturn(List.of());

        // WHEN
        List<Character> actual = characterService.getCharacters(null, null, null, null);

        // THEN
        verify(mockCharacterRepo).findAll();
        assertTrue(actual.isEmpty());
    }

    @Test
    void updateCharacter_WhenCharacterExists_ShouldReturnUpdatedCharacter() {
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
    void deleteCharacter_ShouldCallRepositoryDeleteById() {
        //GIVEN
        String id = "1";
        when(mockCharacterRepo.existsById(id)).thenReturn(true);
        doNothing().when(mockCharacterRepo).deleteById(id);

        //WHEN
        characterService.deleteCharacter(id);

        //THEN
        verify(mockCharacterRepo).existsById(id);
        verify(mockCharacterRepo).deleteById(id);
    }

    @Test
    void getCharacterById_WhenCharacterExists_ShouldReturnCharacter() {
        // GIVEN
        when(mockCharacterRepo.findById("1")).thenReturn(Optional.of(testChar));

        // WHEN
        Optional<Character> actual = characterService.getCharacterById("1");

        // THEN
        verify(mockCharacterRepo).findById("1");
        assertEquals(Optional.of(testChar), actual);
    }

    @Test
    void getCharacterById_WhenCharacterNotFound_ShouldReturnEmptyOptional() {
        // GIVEN
        when(mockCharacterRepo.findById("1")).thenReturn(Optional.empty());

        // WHEN
        Optional<Character> actual = characterService.getCharacterById("1");

        // THEN
        verify(mockCharacterRepo).findById("1");
        assertEquals(Optional.empty(), actual);
    }
}