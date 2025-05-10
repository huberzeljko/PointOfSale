package com.superb.pos.shared.models

data class ProductsSorter(
    val by: Field,
    val descending: Boolean = false,
) {
    companion object {
        val default = ProductsSorter(by = Field.NAME, descending = false)
    }

    enum class Field { NAME, PRICE }
}