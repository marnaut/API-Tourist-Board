package com.mevludin.APITouristBoard.controller;

import com.mevludin.APITouristBoard.controllers.CountryController;
import com.mevludin.APITouristBoard.models.Country;
import com.mevludin.APITouristBoard.services.CountryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CountryController.class)

public class CountryControllerTest {

    @Configuration
    @EnableWebSecurity
    static class Config{
        @Autowired
        public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
            auth.inMemoryAuthentication().withUser("editor").password("test").roles("EDITOR");
            auth.inMemoryAuthentication().withUser("admin").password("test").roles("ADMIN");
        }
    }

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CountryService countryService;


    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void shouldReturn200WhenSendingRequestToControllerWithRoleUser() throws Exception {
        mockMvc.perform(get("/api/v1/countries")).andExpect(status().isOk());
    }


}
