package br.com.article.optimisticlock.inventory.repository

import br.com.article.optimisticlock.inventory.model.Inventory
import org.springframework.data.jpa.repository.JpaRepository

interface InventoryRepository : JpaRepository<Inventory, Long>