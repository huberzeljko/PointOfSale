package com.superb.pos.shared.models

data class ProductsFilter(
    val searchQuery: String = "",
    val minPrice: Double? = null,
    val maxPrice: Double? = null,
    val paging: Paging
)