package com.appRachunek.AplikacjaDoRozliczaniaDelegacji.person;

import com.appRachunek.AplikacjaDoRozliczaniaDelegacji.SortType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/functions")
class PersonController {
    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    List<PersonDto> getAll(@RequestParam(defaultValue = "ASC") SortType sortType) {
        return personService.getAllFunctions(sortType);
    }

    @PostMapping
    PersonDto add(@Validated(value = AddFunction.class) @RequestBody PersonDto person) {
        return personService.addFunction(person);
    }

    @PutMapping("/{name}")
    PersonDto update(@PathVariable String name, @Validated(value = UpdateFunction.class) @RequestBody PersonDto person) {
        return personService.updateFunction(name, person);
    }

    @GetMapping("/{name}")
    PersonDto getWithId(String name) {
        return personService.getFunction(name);
    }

    @DeleteMapping("/{name}")
    PersonDto delete(@PathVariable String name) {
        return personService.deleteFunction(name);
    }


}
