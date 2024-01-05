package com.appRachunek.AplikacjaDoRozliczaniaDelegacji.person.args;


import com.appRachunek.AplikacjaDoRozliczaniaDelegacji.person.Person;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Stream;

public class GetAllFunctionArgumentProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(
                Arguments.of(
                        Arrays.asList(
                                new Person("Rutkowski", "Sędzia"),
                                new Person("Iksinski", "Sędzia"),
                                new Person("Wolf", "Obserwator")
                        ), Arrays.asList(
                                new Person(1L, "Rutkowski", "Sędzia"),
                                new Person(2L, "Iksinski", "Sędzia"),
                                new Person(3L, "Wolf", "Obserwator")
                        )
                ),
                Arguments.of(Collections.emptyList(), Collections.emptyList())
        );
    }
}
