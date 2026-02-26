package com.victor.portfolio.restapi.product;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProductReadIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void listCategories_withoutToken_isPublic_andReturnsSeededData() throws Exception {
        mockMvc.perform(get("/api/categories")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()", greaterThanOrEqualTo(3)))
                .andExpect(jsonPath("$.data[0].code").isNotEmpty())
                .andExpect(jsonPath("$.data[0].name").isNotEmpty());
    }

    @Test
    void listProducts_withPaging_returnsPagedResponseMetadata() throws Exception {
        // data.sql has 4 products; requesting size=2 should yield 2 pages total
        mockMvc.perform(get("/api/products")
                        .param("page", "0")
                        .param("size", "2")
                        .param("sort", "price,desc")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data.items").isArray())
                .andExpect(jsonPath("$.data.items.length()", is(2)))
                .andExpect(jsonPath("$.data.page").value(0))
                .andExpect(jsonPath("$.data.size").value(2))
                .andExpect(jsonPath("$.data.totalItems").value(4))
                .andExpect(jsonPath("$.data.totalPages").value(2))
                .andExpect(jsonPath("$.data.hasNext").value(true))
                .andExpect(jsonPath("$.data.hasPrevious").value(false))
                .andExpect(jsonPath("$.data.sort", containsString("price: DESC")));
    }
}