package com.superb.pos.services

import com.superb.pos.data.*
import com.superb.pos.domain.Product
import com.superb.pos.shared.helpers.paged
import com.superb.pos.shared.models.ProductsFilter
import com.superb.pos.shared.models.ProductsSorter
import kotlinx.coroutines.delay

class ProductService(private val repo: ProductRepository) {
    companion object {
        val default = ProductService(repo = FixturesProductRepository())
    }

    @Throws(Throwable::class)
    suspend fun getProducts(filter: ProductsFilter, sorter: ProductsSorter): List<Product> {
        return repo
            .findAll()
            .filtered(filter)
            .sorted(sorter)
            .paged(filter.paging)
    }

    private fun List<Product>.filtered(filter: ProductsFilter): List<Product> {
        val predicates = mutableListOf<(Product) -> Boolean>()

        if (filter.searchQuery.isNotBlank()) {
            predicates += { it.name.contains(filter.searchQuery, ignoreCase = true) }
        }

        if (filter.minPrice != null) {
            predicates += { it.price >= filter.minPrice }
        }

        if (filter.maxPrice != null) {
            predicates += { it.price <= filter.maxPrice }
        }

        return if (predicates.isEmpty()) {
            this
        } else {
            this.filter { p -> predicates.all { pred -> pred(p) } }
        }
    }

    private fun List<Product>.sorted(sorting: ProductsSorter) = when (sorting.by) {
        ProductsSorter.Field.NAME  ->
            if (sorting.descending)
                sortedByDescending { it.name }
            else
                sortedBy { it.name }

        ProductsSorter.Field.PRICE ->
            if (sorting.descending)
                sortedByDescending { it.price }
            else
                sortedBy { it.price }
    }
}

