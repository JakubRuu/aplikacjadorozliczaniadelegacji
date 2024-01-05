package com.appRachunek.AplikacjaDoRozliczaniaDelegacji.field;

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
@RequestMapping("/fields")
public class FieldController {
    private final FieldService fieldService;

    FieldController(FieldService fieldService) {
        this.fieldService = fieldService;
    }

    @GetMapping
    List<Field> getAllField(@RequestParam SortType sortType) {
        return fieldService.getAllField(sortType);
    }

    @PostMapping
    Field add(@Validated(value = AddField.class) @RequestBody Field field) {
        return fieldService.addField(field);
    }

    @PutMapping("/{id}")
    Field update(@PathVariable String id, @Validated(value = UpdateField.class) @RequestBody Field field) {
        return fieldService.updateField(id, field);
    }

    @GetMapping("/{id}")
    Field getWithId(String id) {
        return fieldService.getField(id);
    }

    @DeleteMapping("/{id}")
    Field delete(@PathVariable String id) {
        return fieldService.deleteField(id);
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