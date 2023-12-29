package com.appRachunek.AplikacjaDoRozliczaniaDelegacji.stanowisko.args;

import com.appRachunek.AplikacjaDoRozliczaniaDelegacji.stanowisko.Function;
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
                                new Function("Rutkowski", "Sędzia"),
                                new Function("Iksinski", "Sędzia"),
                                new Function("Wolf", "Obserwator")
                        ), Arrays.asList(
                                new Function(1L, "Rutkowski", "Sędzia"),
                                new Function(2L, "Iksinski", "Sędzia"),
                                new Function(3L, "Wolf", "Obserwator")
                        )
                ),
                Arguments.of(Collections.emptyList(), Collections.emptyList())
        );
    }
}
