package com.github.ramezch.spring;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/asterix/characters/")
public class CharacterController {
    private final CharacterRepository characterRepo;

    @GetMapping("")
    public List<Character> getCharacters(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer age,
            @RequestParam(required = false) String profession) {

        if (name != null) {
            return characterRepo.findByNameContainingIgnoreCase(name);
        }
        if (age != null) {
            return characterRepo.findByAge(age);
        }
        if (profession != null) {
            return characterRepo.findByProfessionContainingIgnoreCase(profession);
        }
        return characterRepo.findAll();
    }

    @PostMapping("")
    public Character createCharacter(@RequestBody Character character) {
        return characterRepo.save(character);
    }

    @PutMapping("{id}")
    public Character updateCharacter(@PathVariable String id, @RequestBody Character newCharacterData) {
        Optional<Character> characterOptional = characterRepo.findById(id);
        if (characterOptional.isPresent()) {
            Character character = characterOptional.get();
            character = character.withName(newCharacterData.name()).withProfession(newCharacterData.profession()).withAge(newCharacterData.age());
            return characterRepo.save(character);
        }
        return null;
    }

    @DeleteMapping("{id}")
    public void deleteCharacter(@PathVariable String id) {
        if (characterRepo.existsById(id)) {
            characterRepo.deleteById(id);
            System.out.println("Character with id: " + id + " has been deleted.");
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