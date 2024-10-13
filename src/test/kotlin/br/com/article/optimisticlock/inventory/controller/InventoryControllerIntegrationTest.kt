package br.com.article.optimisticlock.inventory.controller

import br.com.article.optimisticlock.inventory.service.InventoryService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@SpringBootTest
@AutoConfigureMockMvc
class InventoryControllerIntegrationTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var service: InventoryService

    @Test
    fun `decrementInventory success`() {
        mockMvc.perform(put("/inventory/1/decrement"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.quantity").value(9))
    }

    @Test
    fun `decrementInventory not found`() {
        mockMvc.perform(put("/inventory/999/decrement"))
            .andExpect(status().isNotFound)
    }

    @Test
    fun `decrementInventory conflict`() {
        mockMvc.perform(put("/inventory/1/decrement"))
            .andExpect(status().isConflict)
    }
}