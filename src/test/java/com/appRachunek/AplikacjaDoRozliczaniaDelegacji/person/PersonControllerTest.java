package com.appRachunek.AplikacjaDoRozliczaniaDelegacji.person;

import com.appRachunek.AplikacjaDoRozliczaniaDelegacji.SortType;
import com.appRachunek.AplikacjaDoRozliczaniaDelegacji.person.args.SortTypeArgumentProvider;
import com.appRachunek.AplikacjaDoRozliczaniaDelegacji.person.args.ValidateAddFunctionArgumentProvider;
import com.appRachunek.AplikacjaDoRozliczaniaDelegacji.person.args.ValidateUpdatedFunctionArgumentProvider;
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

import java.util.*;

import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PersonController.class)
class PersonControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    PersonService personService;

    @Test
    void when_add_valid_function_then_should_be_saved() throws Exception {
        //given
        PersonDto function = new PersonDto("Rutkowski", "Sędzia");
        PersonDto addedFunction = new PersonDto(1L, "Rutkowski", "Sędzia");
        Mockito.when(personService.addFunction(function)).thenReturn(addedFunction);
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


    @ParameterizedTest
    @ArgumentsSource(ValidateAddFunctionArgumentProvider.class)
    void when_add_invalid_function_arg1_then_exception_should_be_thrown_with_arg2_details(String arg1, String[] arg2) throws Exception {
        //given
        //then
        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/functions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(arg1)
                ).andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", equalTo(400)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", equalToIgnoringCase("Bad Request")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.details.name").value(containsInAnyOrder(arg2)));
    }


    @Test
    void when_add_already_existing_organization_then_exception_should_be_thrown() throws Exception {
        //given
        PersonDto person = new PersonDto("Rutkowski", "Sędzia");
        Mockito.when(personService.addFunction(person)).thenThrow(new IllegalArgumentException("organization already exists!"));
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
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", equalTo(400)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", equalTo("Bad Request")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.details", equalTo("organization already exists!")));
    }

    @Test
    void when_delete_existing_function_then_it_should_be_deleted() throws Exception {
        //given
        String name = "Rutkowski";
        PersonDto deletedFunction = new PersonDto(1L, name, "Sędzia");
        Mockito.when(personService.deleteFunction("Rutkowski")).thenReturn(deletedFunction);

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
        Mockito.when(personService.deleteFunction(name)).thenThrow(new NoSuchElementException("No organization found"));
        //then
        //when
        mockMvc.perform(MockMvcRequestBuilders.delete("/functions/" + name)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", equalTo(404)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", equalToIgnoringCase("Not found")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.details", equalToIgnoringCase("No organization found")));
    }

    @Test
    void when_update_non_existing_function_then_exception_should_be_thrown() throws Exception {
        //given
        String name = "Rutkowski";
        PersonDto person = new PersonDto(name, "TEST");
        Mockito.when(personService.updateFunction(name, person)).thenThrow(new NoSuchElementException("No organization found"));
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
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", equalTo(404)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", equalToIgnoringCase("Not found")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.details", equalToIgnoringCase("No organization found")));
    }

    @Test
    void when_update_existing_function_then_function_should_be_update() throws Exception {
        //given
        String name = "Rutkowski";
        PersonDto function = new PersonDto(name, "Sędzia");
        PersonDto updatedFunction = new PersonDto(1L, name, "Sędzia");
        Mockito.when(personService.updateFunction(name, function)).thenReturn(updatedFunction);
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
        Mockito.when(personService.getAllFunctions(SortType.ASC)).thenReturn(Collections.emptyList());

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/functions")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @ParameterizedTest
    @ArgumentsSource(SortTypeArgumentProvider.class)
    void when_get_non_empty_function_list_then_array_with_orgs_should_be_returned(String arg1,
                                                                                  SortType arg2) throws Exception {
        //given
        ArgumentCaptor<SortType> sortArgumentCaptor = ArgumentCaptor.forClass(SortType.class);
        Mockito.when(personService.getAllFunctions(arg2)).thenReturn(
                Arrays.asList(
                        new PersonDto("Rutkowski", "Sędzia"),
                        new PersonDto("Iksiński", "Sędzia asystent")
                )
        );
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/functions" + arg1)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));
        Mockito.verify(personService).getAllFunctions(sortArgumentCaptor.capture());
        Assertions.assertEquals(arg2, sortArgumentCaptor.getValue());
    }

    @ParameterizedTest
    @ArgumentsSource(ValidateUpdatedFunctionArgumentProvider.class)
    void when_updated_invalid_function_arg1_then_validation_should_happen(String arg1, boolean result, List<String> arg2) throws Exception {
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
                    .andExpect(MockMvcResultMatchers.jsonPath("$.code", equalTo(400)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.message", equalToIgnoringCase("Bad Request")))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.details.name", equalTo(arg2)));
        }
    }

}
