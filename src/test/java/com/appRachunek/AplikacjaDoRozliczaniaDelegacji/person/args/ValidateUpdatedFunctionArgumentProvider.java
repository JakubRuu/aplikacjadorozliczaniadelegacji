package com.appRachunek.AplikacjaDoRozliczaniaDelegacji.person.args;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Stream;

public class ValidateUpdatedFunctionArgumentProvider implements ArgumentsProvider {
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
                        false,
                        Arrays.asList("size must be between 2 and 20")
                ),
                Arguments.of(
                                        """
                                        {
                                            "function":"Sędzia",       
                                            "name":""
                                        }
                                        """,
                        false,
                        Arrays.asList( "size must be between 2 and 20")
                ),
                Arguments.of(
                        """
                                        {
                                            "function":"Sędzia"     
                                        }
                                        """,
                        true,
                        Collections.emptyList()
                )
        );
    }
}
