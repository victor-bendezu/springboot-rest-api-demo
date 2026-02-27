package com.victor.portfolio.restapi.product;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProductValidationIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createProduct_shouldReturn400_whenPayloadIsInvalid() throws Exception {
        String token = loginAsAdminAndGetJwt();

        // Missing required fields on purpose
        String invalidBody = """
                {
                  "sku": "",
                  "name": "",
                  "price": null,
                  "categoryId": null,
                  "active": null
                }
                """;

        mockMvc.perform(post("/api/products")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    private String loginAsAdminAndGetJwt() throws Exception {
        String loginBody = """
                {
                  "username": "admin",
                  "password": "admin123"
                }
                """;

        String response = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return JsonPath.read(response, "$.data.accessToken");
    }
}