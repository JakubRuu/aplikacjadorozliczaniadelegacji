package com.appRachunek.AplikacjaDoRozliczaniaDelegacji.stanowisko;

import com.appRachunek.AplikacjaDoRozliczaniaDelegacji.SortType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/funkcja")
class FunkcjaKontroler {
    private final FunkcjaSerwis funkcjaSerwis;

    public FunkcjaKontroler(FunkcjaSerwis funkcjaSerwis) {
        this.funkcjaSerwis = funkcjaSerwis;
    }

    @GetMapping
    List<Funkcja> pobierzWszystko(@RequestParam SortType sortType) {
        return funkcjaSerwis.pobracWszystkieDane(sortType);
    }

    @PostMapping
    Funkcja dodaj(@Validated(value = DodajFunkcje.class) @RequestBody Funkcja funkcja) {
        return funkcjaSerwis.dodajFunkcje(funkcja);
    }

    @PutMapping("/{id}")
    Funkcja zaktualizuj(@PathVariable String id, @Validated(value = ZaktualizujFunkcje.class) @RequestBody Funkcja funkcja) {
        return funkcjaSerwis.zakutalizujDane(id, funkcja);
    }

    @GetMapping("/{id}")
    Funkcja pobierzPoId(String id) {
        return funkcjaSerwis.pobranieFunkcjiPoId(id);
    }

    @DeleteMapping("/{id}")
    Funkcja usun(@PathVariable String id) {
        return funkcjaSerwis.usunFunkcje(id);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<Object> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = NoSuchElementException.class)
    ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
