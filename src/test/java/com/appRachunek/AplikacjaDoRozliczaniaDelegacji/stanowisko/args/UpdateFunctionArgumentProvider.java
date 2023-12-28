package com.appRachunek.AplikacjaDoRozliczaniaDelegacji.stanowisko.args;

import com.appRachunek.AplikacjaDoRozliczaniaDelegacji.stanowisko.Funkcja;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class UpdateFunctionArgumentProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(
                //name
                //existing fu
                //to update
                //expected
                Arguments.of(
                        "1",
                        new Funkcja("1", "Sędzia"),
                        new Funkcja(null, "Sędzia Asystent"),
                        new Funkcja("1", "Sędzia Asystent")
                ),
                Arguments.of(
                        "1",
                        new Funkcja("1", "Sędzia"),
                        new Funkcja("2", null),
                        new Funkcja("2", "Sędzia")
                ),
                Arguments.of(
                        "1",
                        new Funkcja("1", "Sędzia"),
                        new Funkcja("2", "Sędzia Asystent"),
                        new Funkcja("2", "Sędzia Asystent")
                ),
                Arguments.of(
                        "1",
                        new Funkcja("1", "Sędzia"),
                        new Funkcja(null, null),
                        new Funkcja("1", "Sędzia")
                )
        );
    }
}
