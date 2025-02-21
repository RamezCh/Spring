package com.github.ramezch.spring.characters;

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
    private final AsterixService asterixService;

    @GetMapping
    public List<Character> getCharacters(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer age,
            @RequestParam(required = false) String profession,
            @RequestParam(required = false) Integer maxAge) {
        return asterixService.getCharacters(name, age, profession, maxAge);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CharacterDTO createCharacter(@RequestBody CharacterDTO character) {
        asterixService.createCharacter(character);
        return character;
    }

    @PutMapping("{id}")
    public CharacterDTO updateCharacter(@PathVariable String id, @RequestBody CharacterDTO characterDTO) {
        asterixService.updateCharacter(id, characterDTO);
        return characterDTO;
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCharacter(@PathVariable String id) {
        asterixService.deleteCharacter(id);
        logger.info("Character with ID {} has been deleted.", id);
    }

    @GetMapping("average-age")
    public double getAverageAge() {
        return asterixService.getAverageAge();
    }
}