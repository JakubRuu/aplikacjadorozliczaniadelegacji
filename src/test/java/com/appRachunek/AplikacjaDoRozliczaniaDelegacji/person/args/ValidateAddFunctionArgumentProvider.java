package com.appRachunek.AplikacjaDoRozliczaniaDelegacji.person.args;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Stream;

public class ValidateAddFunctionArgumentProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(
                Arguments.of(
                        """
                                        {
                                            "function":"Sędzia",       
                                            "name":"R"
                                        }
                                        """,
                        new String[]{ "size must be between 2 and 20"}
                ),
                Arguments.of(
                                        """
                                        {
                                            "function":"Sędzia",       
                                            "name":""
                                        }
                                        """,
                        new String[]{ "must not be blank","size must be between 2 and 20"}
                ),
                Arguments.of(
                        """
                                        {
                                            "function":"Sędzia"     
                                        }
                                        """,
                        new String[]{  "must not be blank"}
                )
        );
    }
}
