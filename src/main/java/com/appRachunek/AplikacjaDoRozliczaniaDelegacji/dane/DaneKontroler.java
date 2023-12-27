package com.appRachunek.AplikacjaDoRozliczaniaDelegacji.dane;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/imie")
class DaneKontroler {
    private final DaneSerwis daneSerwis;

    DaneKontroler(DaneSerwis daneSerwis) {
        this.daneSerwis = daneSerwis;
    }

    @GetMapping
    List<Dane> pobierzWszystko() {
        return daneSerwis.pobracWszystkieDane();
    }

    @PostMapping
    Dane dodaj(@Validated(value = DodajDane.class) @RequestBody Dane dane) {
        return daneSerwis.dodajDane(dane);
    }

    @PutMapping("/{imie}")
    Dane zaktualizuj(@PathVariable String imie, @Validated(value = ZaktualizujDane.class) @RequestBody Dane dane) {
        return daneSerwis.zakutalizujDane(imie, dane);
    }

    @DeleteMapping("/{imie}")
    Dane usun(@PathVariable String imie) {
        return daneSerwis.usunDane(imie);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<Object> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String,String> errors= new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error)->{
            String fieldName = ((FieldError)error).getField();
            String errorMessage= error.getDefaultMessage();
            errors.put(fieldName,errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = NoSuchElementException.class)
     ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(value = NoSuchElementException.class)
    ResponseEntity<Object> handleIllegalArgumentException(NoSuchElementException e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
    }

}
