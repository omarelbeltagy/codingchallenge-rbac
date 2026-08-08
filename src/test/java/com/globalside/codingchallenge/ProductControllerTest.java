package com.globalside.codingchallenge.rbac;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {
    @Autowired
    private MockMvc mvc;

    //Correct cases
    @Test
    @WithMockUser(roles = "USER")
    public void requestProductsUrlWithUser() throws Exception {
        mvc
                .perform(get("/products"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void requestProductsUrlWithAdmin() throws Exception {
        mvc
                .perform(get("/products"))
                .andExpect(status().isOk());
    }

    //Wrong cases
    @Test
    public void requestProductsUrlWithWrongPassword() throws Exception {
        mvc
                .perform(get("/products").with(httpBasic("user", "wrongpassword")))
                .andExpect(status().isUnauthorized());
    }


}