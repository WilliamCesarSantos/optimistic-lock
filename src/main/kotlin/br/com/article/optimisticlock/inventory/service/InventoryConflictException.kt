package br.com.article.optimisticlock.inventory.service

class InventoryConflictException(message: String) : RuntimeException(message)
