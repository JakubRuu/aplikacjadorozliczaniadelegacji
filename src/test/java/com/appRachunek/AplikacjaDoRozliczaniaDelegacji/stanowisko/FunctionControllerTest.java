package com.appRachunek.AplikacjaDoRozliczaniaDelegacji.stanowisko;

import com.appRachunek.AplikacjaDoRozliczaniaDelegacji.SortType;
import com.appRachunek.AplikacjaDoRozliczaniaDelegacji.stanowisko.args.SortTypeArgumentProvider;
import com.appRachunek.AplikacjaDoRozliczaniaDelegacji.stanowisko.args.ValidateAddFunctionArgumentProvider;
import com.appRachunek.AplikacjaDoRozliczaniaDelegacji.stanowisko.args.ValidateUpdatedFunctionArgumentProvider;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Collections;
import java.util.NoSuchElementException;

import static org.hamcrest.Matchers.equalTo;

@ExtendWith(SpringExtension.class)
@WebMvcTest(FunctionController.class)
class FunctionControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    FunctionService functionService;

    @Test
    void when_add_valid_function_then_should_be_saved() throws Exception {
        //given
        Function function = new Function("Rutkowski", "Sędzia");
        Function addedFunction = new Function(1L, "Rutkowski", "Sędzia");
        Mockito.when(functionService.addFunction(function)).thenReturn(addedFunction);
        //then
        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/functions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                        {
                                            "function":"Sędzia",       
                                            "name":"Rutkowski"
                                        }
                                        """
                        )
                ).andExpect(MockMvcResultMatchers.jsonPath("$.name", equalTo("Rutkowski")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", equalTo(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.function", equalTo("Sędzia")));
    }

    @Test
    void when_add_invalid_function_then_exception_should_be_thrown() throws Exception {
        //given
        //then
        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/functions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                        {
                                            "function":"Sędzia",
                                            "name":"R"
                                        }
                                        """
                        )
                ).andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", equalTo("size must be between 2 and 20")));
    }

    @ParameterizedTest
    @ArgumentsSource(ValidateAddFunctionArgumentProvider.class)
    void when_add_invalid_function_arg1_then_exception_should_be_thrown_with_arg2_details(String arg1, String arg2) throws Exception {
        //given
        //then
        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/functions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(arg1)
                ).andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", equalTo(arg2)));
    }


    @ParameterizedTest
    @ArgumentsSource(ValidateAddFunctionArgumentProvider.class)
    void when_add_already_existing_organization_then_exception_should_be_thrown() throws Exception {
        //given
        Function function = new Function("Rutkowski", "Sędzia");
        Mockito.when(functionService.addFunction(function)).thenThrow(new IllegalArgumentException("organization already exists!"));
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/functions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                        {
                                            "function":"Sędzia",
                                            "name":"Rutkowski"
                                        }
                                        """
                        )
                ).andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void when_delete_existing_function_then_it_should_be_deleted() throws Exception {
        //given
        String name = "Rutkowski";
        Function deletedFunction = new Function(1L, name, "Sędzia");
        Mockito.when(functionService.deleteFunction("Rutkowski")).thenReturn(deletedFunction);

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.delete("/functions/" + name)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", equalTo("Rutkowski")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", equalTo(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.function", equalTo("Sędzia")));
    }

    @Test
    void when_delete_non_existing_function_then_exception_should_be_thrown() throws Exception {
        //given
        String name = "Rutkowski";
        Mockito.when(functionService.deleteFunction(name)).thenThrow(new NoSuchElementException("No organization found"));
        //then
        //when
        mockMvc.perform(MockMvcRequestBuilders.delete("/functions/" + name)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void when_update_non_existing_function_then_exception_should_be_thrown() throws Exception {
        //given
        String name = "Rutkowski";
        Function function = new Function(name, "TEST");
        Mockito.when(functionService.updateFunction(name, function)).thenThrow(new NoSuchElementException("No organization found"));
        //then
        //when
        mockMvc.perform(MockMvcRequestBuilders.put("/functions/" + name)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                        {
                                            "function":"TEST",
                                            "name":"Rutkowski"
                                        }
                                        """
                        )
                )
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void when_update_existing_function_then_function_should_be_update() throws Exception {
        //given
        String name = "Rutkowski";
        Function function = new Function(name, "Sędzia");
        Function updatedFunction = new Function(1L, name, "Sędzia");
        Mockito.when(functionService.updateFunction(name, function)).thenReturn(updatedFunction);
        //then
        //when
        mockMvc.perform(MockMvcRequestBuilders.put("/functions/" + name)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                        {
                                            "function":"Sędzia",       
                                            "name":"Rutkowski"
                                        }
                                        """
                        )
                ).andExpect(MockMvcResultMatchers.jsonPath("$.name", equalTo("Rutkowski")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", equalTo(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.function", equalTo("Sędzia")));
    }

    @Test
    void when_get_empty_function_list_then_empty_array_should_be_returned() throws Exception {
        //given
        Mockito.when(functionService.getAllFunction(SortType.ASC)).thenReturn(Collections.emptyList());

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/functions")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @ParameterizedTest
    @ArgumentsSource(SortTypeArgumentProvider.class)
    void when_get__non_empty_function_list_then_array_with_orgs_should_be_returned(String arg1,
                                                                                   SortType arg2) throws Exception {
        //given
        ArgumentCaptor<SortType> sortArgumentCaptor = ArgumentCaptor.forClass(SortType.class);
        Mockito.when(functionService.getAllFunction(arg2)).thenReturn(
                Arrays.asList(
                        new Function("Rutkowski", "Sędzia"),
                        new Function("Iksiński", "Sędzia asystent")
                )
        );
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/functions" + arg1)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));
        Mockito.verify(functionService).getAllFunction(sortArgumentCaptor.capture());
        Assertions.assertEquals(arg2, sortArgumentCaptor.getValue());
    }

    @ParameterizedTest
    @ArgumentsSource(ValidateUpdatedFunctionArgumentProvider.class)
    void when_updated_invalid_function_arg1_then_validation_should_happen(String arg1, boolean result, String arg2) throws Exception {
        //given
        String existingFunName = "Rutkowski";
        //then
        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put("/functions/" + existingFunName)
                .contentType(MediaType.APPLICATION_JSON)
                .content(arg1));
        if (result) {
            resultActions.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
        } else {
            resultActions.andExpect(MockMvcResultMatchers.status().is4xxClientError())
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.name", equalTo(arg2)));
        }
    }

}
