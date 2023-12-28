package com.appRachunek.AplikacjaDoRozliczaniaDelegacji.stanowisko.args;

import com.appRachunek.AplikacjaDoRozliczaniaDelegacji.SortType;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.springframework.data.domain.Sort;

import java.util.stream.Stream;

public class SortFunctionArgumentProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(
                Arguments.of(
                        SortType.ASC,
                        Sort.by(Sort.Direction.ASC, "id")
                ),
                Arguments.of(
                        SortType.DESC,
                        Sort.by(Sort.Direction.DESC,"id")
                )


        );
    }
}
