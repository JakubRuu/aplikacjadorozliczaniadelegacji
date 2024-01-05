package com.appRachunek.AplikacjaDoRozliczaniaDelegacji.field;

import com.appRachunek.AplikacjaDoRozliczaniaDelegacji.SortType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fields")
public class FieldController {
    private final FieldService fieldService;

    FieldController(FieldService fieldService) {
        this.fieldService = fieldService;
    }

    @GetMapping
    List<FieldDto> getAllField() {
        return fieldService.getAllField();
    }

    @PostMapping
    FieldDto add(@Validated(value = AddField.class) @RequestBody FieldDto field) {
        return fieldService.addField(field);
    }

    @PutMapping("/{id}")
    FieldDto update(@PathVariable String id, @Validated(value = UpdateField.class) @RequestBody FieldDto field) {
        return fieldService.updateField(id, field);
    }

    @GetMapping("/{id}")
    Field getWithId(String id) {
        return fieldService.getField(id);
    }

    @DeleteMapping("/{id}")
    FieldDto delete(@PathVariable String id) {
        return fieldService.deleteField(id);
    }


}