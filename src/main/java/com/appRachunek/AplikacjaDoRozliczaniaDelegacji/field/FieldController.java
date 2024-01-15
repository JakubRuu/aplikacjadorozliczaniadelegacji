package com.appRachunek.AplikacjaDoRozliczaniaDelegacji.field;

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
    List<FieldDto> getAllField(@RequestParam(required = false)  String identifier,
                               @RequestParam(required = false)   String homeTeam,
                               @RequestParam(required = false)  String fieldName,
                               @RequestParam(required = false) String visitingTeam,
                               @RequestParam(required = false) Integer fieldNo,
                               @RequestParam(required = false) boolean isAvailable,
                               @RequestParam(required = false)  String howManyReferee) {
        return fieldService.getFieldBy(identifier,homeTeam,fieldName,visitingTeam,fieldNo,isAvailable,howManyReferee);
    }

    @GetMapping("/{id}")
    FieldDto getById(String id) {
        return fieldService.getFieldById(id);
    }

    @PostMapping
    FieldDto add(@Validated(value = AddField.class) @RequestBody FieldDto field) {
        return fieldService.addField(field);
    }

    @PutMapping("/{id}")
    FieldDto update(@PathVariable String id, @Validated(value = UpdateField.class) @RequestBody FieldDto field) {
        return fieldService.updateField(id, field);
    }

    @DeleteMapping("/{id}")
    FieldDto delete(@PathVariable String id) {
        return fieldService.deleteField(id);
    }


}