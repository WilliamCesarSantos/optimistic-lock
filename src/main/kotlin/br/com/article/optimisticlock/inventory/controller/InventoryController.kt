package br.com.article.optimisticlock.inventory.controller

import br.com.article.optimisticlock.inventory.model.Inventory
import br.com.article.optimisticlock.inventory.service.InventoryConflictException
import br.com.article.optimisticlock.inventory.service.InventoryNotFoundException
import br.com.article.optimisticlock.inventory.service.InventoryService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/inventory")
class InventoryController(
    private val inventoryService: InventoryService
) {

    @PutMapping("/{productId}/decrement")
    fun decrementInventory(@PathVariable productId: Long): ResponseEntity<Inventory> {
        return try {
            val inventory = inventoryService.decrementInventory(productId)
            ResponseEntity.ok(inventory)
        } catch (e: InventoryNotFoundException) {
            ResponseEntity.notFound().build()
        } catch (e: InventoryConflictException) {
            ResponseEntity.status(HttpStatus.CONFLICT).build()
        }
    }

}