package br.com.article.optimisticlock.inventory.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.Version
import org.jetbrains.annotations.NotNull

@Entity
@Table
class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @NotNull
    var quantity: Int = 0

    @Version
    var version: Int = 0

    constructor()

    constructor(id: Long, quantity: Int, version: Int) {
        this.id = id
        this.quantity = quantity
        this.version = version
    }


}