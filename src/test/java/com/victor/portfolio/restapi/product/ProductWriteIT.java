package com.victor.portfolio.restapi.product;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProductWriteIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createProduct_withUserRoleToken_isForbidden() throws Exception {
        String token = loginAndGetToken("user", "user123");

        mockMvc.perform(post("/api/products")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "sku": "SKU-9999",
                                  "name": "Test Product",
                                  "price": 10.0,
                                  "categoryId": 1,
                                  "active": true
                                }
                                """))
                .andExpect(status().isForbidden());
    }

    @Test
    void createProduct_withAdminToken_andNonExistingCategory_returns404() throws Exception {
        String token = loginAndGetToken("admin", "admin123");

        mockMvc.perform(post("/api/products")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "sku": "SKU-8888",
                                  "name": "Invalid Category Product",
                                  "price": 10.0,
                                  "categoryId": 999,
                                  "active": true
                                }
                                """))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.message").value("Category not found: 999"));
    }

    private String loginAndGetToken(String username, String password) throws Exception {
        String body = """
                {
                  "username": "%s",
                  "password": "%s"
                }
                """.formatted(username, password);

        MvcResult result = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andReturn();

        JsonNode json = objectMapper.readTree(result.getResponse().getContentAsString());
        return json.get("data").get("accessToken").asText();
    }
}