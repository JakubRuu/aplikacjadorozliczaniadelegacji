package com.appRachunek.AplikacjaDoRozliczaniaDelegacji.stanowisko;

import com.appRachunek.AplikacjaDoRozliczaniaDelegacji.SortType;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.NoSuchElementException;

@Service
class FunctionService {
    private final FunctionRepository functionRepository;


    public FunctionService(FunctionRepository functionRepository) {
        this.functionRepository = functionRepository;
    }

    List<Function> getAllFunction(@RequestParam(defaultValue = "ASC") SortType sortType) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortType.name()), "id");
        return functionRepository.findAll(sort);
    }

    Function getFunction(String name) {
        return functionRepository.findByName(name).orElseThrow(() -> new NoSuchElementException("Brak funkcji!"));
    }


    Function addFunction(Function function) {
        functionRepository.findByName(function.getName()).ifPresent(d -> {
            throw new IllegalArgumentException("Dane się powtarzają");
        });
        return functionRepository.save(function);
    }

    Function deleteFunction(String name) {
        Function function = functionRepository.findByName(name).orElseThrow(() -> new NoSuchElementException(""));
        functionRepository.deleteById(function.getId());
        return function;
    }

    Function updateFunction(String name, Function function) {
        Function updatedFunction = functionRepository
                .findByName(name)
                .orElseThrow(() -> new NoSuchElementException(""));

        if (function.getName() != null && !function.getName().equals(updatedFunction.getName())) {
            functionRepository.findByName(function.getName())
                    .ifPresent(o -> {
                        throw new IllegalArgumentException("Dane się powtarzają");
                    });
            updatedFunction.setName(function.getName());
        }
        if (function.getFunction() != null) {
            updatedFunction.setFunction(function.getFunction());
        }
        return functionRepository.save(updatedFunction);
    }
}
