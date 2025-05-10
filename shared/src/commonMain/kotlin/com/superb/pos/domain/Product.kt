package com.superb.pos.domain

data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    val category: String? = null,
    val description: String? = null
)