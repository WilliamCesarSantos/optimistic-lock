package br.com.article.optimisticlock.inventory.service

import br.com.article.optimisticlock.inventory.model.Inventory
import br.com.article.optimisticlock.inventory.repository.InventoryRepository
import jakarta.persistence.OptimisticLockException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.any
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock
import java.util.*
import kotlin.test.assertEquals


class InventoryServiceTest {

    private lateinit var inventoryRepository: InventoryRepository
    private lateinit var inventoryService: InventoryService

    @BeforeEach
    fun setup() {
        inventoryRepository = mock { }
        inventoryService = InventoryService(inventoryRepository)
    }

    @Test
    fun `decrementInventory success`() {
        val inventory = Inventory(1L, 10, 0)
        `when`(inventoryRepository.findById(1L)).thenReturn(Optional.of(inventory))
        `when`(inventoryRepository.save(any())).thenReturn(inventory)

        val result = inventoryService.decrementInventory(1L)

        assertEquals(9, result.quantity)
    }

    @Test
    fun `decrementInventory not found`() {
        `when`(inventoryRepository.findById(1L)).thenReturn(Optional.empty())

        assertThrows<InventoryNotFoundException> {
            inventoryService.decrementInventory(1L)
        }
    }

    @Test
    fun `decrementInventory conflict`() {
        val inventory = Inventory(1L, 10, 0)
        `when`(inventoryRepository.findById(1L)).thenReturn(Optional.of(inventory))
        `when`(inventoryRepository.save(any())).thenThrow(OptimisticLockException::class.java)

        assertThrows<InventoryConflictException> {
            inventoryService.decrementInventory(1L)
        }
    }

}