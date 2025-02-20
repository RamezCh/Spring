package com.github.ramezch.spring;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/asterix/characters")
public class CharacterController {
    private static final Logger logger = LoggerFactory.getLogger(CharacterController.class);
    private final CharacterRepository characterRepo;

    @GetMapping
    public List<Character> getCharacters(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer age,
            @RequestParam(required = false) String profession) {

        if (name != null) return characterRepo.findByNameContainingIgnoreCase(name);
        if (age != null) return characterRepo.findByAge(age);
        if (profession != null) return characterRepo.findByProfessionContainingIgnoreCase(profession);

        return characterRepo.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Character createCharacter(@RequestBody Character character) {
        return characterRepo.save(character);
    }

    @PutMapping("{id}")
    public Character updateCharacter(@PathVariable String id, @RequestBody Character newCharacterData) {
        return characterRepo.findById(id)
                .map(character -> {
                    Character updatedCharacter = character.withName(newCharacterData.name())
                            .withProfession(newCharacterData.profession())
                            .withAge(newCharacterData.age());
                    return characterRepo.save(updatedCharacter);
                })
                .orElseThrow(() -> new CharacterNotFoundException("Character with ID " + id + " not found"));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCharacter(@PathVariable String id) {
        if (characterRepo.existsById(id)) {
            characterRepo.deleteById(id);
            logger.info("Character with ID {} has been deleted.", id);
        } else {
            throw new CharacterNotFoundException("Character with ID " + id + " not found");
        }
    }

    @GetMapping("average-age")
    public double getAverageAge() {
        List<Character> characters = characterRepo.findAll();
        return characters.stream()
                .mapToInt(Character::age)
                .average()
                .orElse(0.0);
    }
}