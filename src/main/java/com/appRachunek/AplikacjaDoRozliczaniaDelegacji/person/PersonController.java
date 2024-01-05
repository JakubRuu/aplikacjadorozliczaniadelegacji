package com.appRachunek.AplikacjaDoRozliczaniaDelegacji.person;

import com.appRachunek.AplikacjaDoRozliczaniaDelegacji.Error;
import com.appRachunek.AplikacjaDoRozliczaniaDelegacji.SortType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/functions")
class PersonController {
    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    List<Person> getAll(@RequestParam SortType sortType) {
        return personService.getAllFunction(sortType);
    }

    @PostMapping
    Person add(@Validated(value = AddFunction.class) @RequestBody Person person) {
        return personService.addFunction(person);
    }

    @PutMapping("/{name}")
    Person update(@PathVariable String name, @Validated(value = UpdateFunction.class) @RequestBody Person person) {
        return personService.updateFunction(name, person);
    }

    @GetMapping("/{name}")
    Person getWithId(String name) {
        return personService.getFunction(name);
    }

    @DeleteMapping("/{name}")
    Person delete(@PathVariable String name) {
        return personService.deleteFunction(name);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<Error<Map<String, List<String>>>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, List<String>> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            List<String> params = errors.getOrDefault(fieldName, new ArrayList<>());
            params.add(errorMessage);
            errors.put(fieldName, params);
        });
        return new ResponseEntity<>(new Error<>(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                errors),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = NoSuchElementException.class)
    ResponseEntity<Error<String>> handleNoSuchElementException(NoSuchElementException e) {
        return new ResponseEntity<>(new Error<>(HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    ResponseEntity<Error<String>> handleIllegalArgumentException(IllegalArgumentException e) {
        return new ResponseEntity<>(new Error<>(HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                e.getMessage()), HttpStatus.BAD_REQUEST);
    }

}
