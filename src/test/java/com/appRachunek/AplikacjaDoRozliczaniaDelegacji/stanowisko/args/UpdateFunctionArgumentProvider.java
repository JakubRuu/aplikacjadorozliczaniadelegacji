package com.appRachunek.AplikacjaDoRozliczaniaDelegacji.stanowisko.args;


import com.appRachunek.AplikacjaDoRozliczaniaDelegacji.stanowisko.Function;
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
                        new Function("1", "Sędzia"),
                        new Function(null, "Sędzia Asystent"),
                        new Function("1", "Sędzia Asystent")
                ),
                Arguments.of(
                        "1",
                        new Function("1", "Sędzia"),
                        new Function("2", null),
                        new Function("2", "Sędzia")
                ),
                Arguments.of(
                        "1",
                        new Function("1", "Sędzia"),
                        new Function("2", "Sędzia Asystent"),
                        new Function("2", "Sędzia Asystent")
                ),
                Arguments.of(
                        "1",
                        new Function("1", "Sędzia"),
                        new Function(null, null),
                        new Function("1", "Sędzia")
                )
        );
    }
}
