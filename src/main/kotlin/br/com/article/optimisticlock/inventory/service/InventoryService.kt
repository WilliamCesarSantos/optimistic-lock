package br.com.article.optimisticlock.inventory.service

import br.com.article.optimisticlock.inventory.model.Inventory
import br.com.article.optimisticlock.inventory.repository.InventoryRepository
import jakarta.persistence.OptimisticLockException
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class InventoryService(
    private val inventoryRepository: InventoryRepository
) {

    @Transactional
    fun decrementInventory(productId: Long): Inventory {
        val inventory = inventoryRepository.findById(productId)
            .orElseThrow { InventoryNotFoundException("Inventory not found for product ID: $productId") }
        inventory.quantity = inventory.quantity - 1
        return try {
            inventoryRepository.save(inventory)
        } catch (e: OptimisticLockException) {
            throw InventoryConflictException("Inventory conflict for product ID: $productId")
        }
    }
}