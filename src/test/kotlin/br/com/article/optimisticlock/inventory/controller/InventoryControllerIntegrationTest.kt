package br.com.article.optimisticlock.inventory.controller

import br.com.article.optimisticlock.inventory.model.Inventory
import br.com.article.optimisticlock.inventory.service.InventoryConflictException
import br.com.article.optimisticlock.inventory.service.InventoryNotFoundException
import br.com.article.optimisticlock.inventory.service.InventoryService
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
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
        `when`(service.decrementInventory(1))
            .thenReturn(Inventory(1, 9, 1))

        mockMvc.perform(put("/inventory/1/decrement"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.quantity").value(9))
    }

    @Test
    fun `decrementInventory not found`() {
        `when`(service.decrementInventory(999))
            .thenThrow(InventoryNotFoundException::class.java)

        mockMvc.perform(put("/inventory/999/decrement"))
            .andExpect(status().isNotFound)
    }

    @Test
    fun `decrementInventory conflict`() {
        `when`(service.decrementInventory(1))
            .thenThrow(InventoryConflictException::class.java)

        mockMvc.perform(put("/inventory/1/decrement"))
            .andExpect(status().isConflict)
    }
}