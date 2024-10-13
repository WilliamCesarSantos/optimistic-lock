package br.com.article.optimisticlock.inventory.controller

import br.com.article.optimisticlock.inventory.service.InventoryConflictException
import br.com.article.optimisticlock.inventory.service.InventoryNotFoundException
import org.hibernate.StaleObjectStateException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ControllerAdvice

@ControllerAdvice
class ControllerAdvice {

    @ExceptionHandler(InventoryNotFoundException::class)
    fun handleInventoryNotFoundException(e: InventoryNotFoundException): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.message)
    }

    @ExceptionHandler(InventoryConflictException::class)
    fun handleInventoryConflictException(e: InventoryConflictException): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.message)
    }

    @ExceptionHandler(StaleObjectStateException::class)
    fun handleStaleObjectStateException(e: StaleObjectStateException): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Object was updated or deleted by another user")
    }

}