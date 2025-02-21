package com.github.ramezch.spring.characters;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AsterixService {
    private final CharacterRepository characterRepo;
    private final IdService idService;

    public List<Character> getCharacters(String name, Integer age, String profession, Integer maxAge) {
        if (name != null) return characterRepo.findByNameContainingIgnoreCase(name);
        if (age != null) return characterRepo.findByAge(age);
        if (profession != null) return characterRepo.findByProfessionContainingIgnoreCase(profession);
        if (maxAge != null) return characterRepo.findByAgeLessThanEqual(maxAge);

        return characterRepo.findAll();
    }

    public Optional<Character> getCharacterById(String id) {
        return characterRepo.findById(id);
    }

    public Character createCharacter(CharacterDTO character) {
        String uniqueID = idService.randomId();
        Character newCharacter = new Character(uniqueID, character.name(), character.age(), character.profession());
        return characterRepo.save(newCharacter);
    }

    public Character updateCharacter(String id, CharacterDTO characterDTO) {
        return characterRepo.findById(id)
                .map(character -> {
                    Character updatedCharacter = character.withName(characterDTO.name())
                            .withAge(characterDTO.age())
                            .withProfession(characterDTO.profession());
                    return characterRepo.save(updatedCharacter);
                })
                .orElseThrow(() -> new CharacterNotFoundException("Character with ID " + id + " not found"));
    }

    public void deleteCharacter(String id) {
        if (characterRepo.existsById(id)) {
            characterRepo.deleteById(id);
        } else {
            throw new CharacterNotFoundException("Character with ID " + id + " not found");
        }
    }

    public double getAverageAge() {
        List<Character> characters = characterRepo.findAll();
        return characters.stream()
                .mapToInt(Character::age)
                .average()
                .orElse(0.0);
    }
}