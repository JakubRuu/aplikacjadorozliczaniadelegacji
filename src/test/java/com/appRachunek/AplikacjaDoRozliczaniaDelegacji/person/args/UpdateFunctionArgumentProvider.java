package com.appRachunek.AplikacjaDoRozliczaniaDelegacji.person.args;



import com.appRachunek.AplikacjaDoRozliczaniaDelegacji.person.Person;
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
                        new Person("1", "Sędzia"),
                        new Person(null, "Sędzia Asystent"),
                        new Person("1", "Sędzia Asystent")
                ),
                Arguments.of(
                        "1",
                        new Person("1", "Sędzia"),
                        new Person("2", null),
                        new Person("2", "Sędzia")
                ),
                Arguments.of(
                        "1",
                        new Person("1", "Sędzia"),
                        new Person("2", "Sędzia Asystent"),
                        new Person("2", "Sędzia Asystent")
                ),
                Arguments.of(
                        "1",
                        new Person("1", "Sędzia"),
                        new Person(null, null),
                        new Person("1", "Sędzia")
                )
        );
    }
}
