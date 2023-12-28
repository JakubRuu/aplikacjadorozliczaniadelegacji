package com.appRachunek.AplikacjaDoRozliczaniaDelegacji.stanowisko.args;

import com.appRachunek.AplikacjaDoRozliczaniaDelegacji.stanowisko.Funkcja;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Stream;

public class PobierzIdFunkcjeArgProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(
                Arguments.of(
                        Arrays.asList(
                                new Funkcja("1", "Sędzia"),
                                new Funkcja("2", "Sędzia asystent"),
                                new Funkcja("3", "Obserwator")
                        ), "1",
                        Optional.of(new Funkcja("1", "Sędzia"))
                ),
                Arguments.of(
                        Collections.emptyList(),
                        "1",
                        Optional.empty()),
                Arguments.of(
                        Arrays.asList(
                                new Funkcja("1", "Sędzia"),
                                new Funkcja("2", "Sędzia asystent"),
                                new Funkcja("3", "Obserwator")
                        ),
                        "Test",
                        Optional.empty()

                )
        );
    }
}
