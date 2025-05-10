package com.superb.pos.shared.helpers

import com.superb.pos.shared.models.Paging

fun <T> Collection<T>.paged(paging: Paging): List<T> =
    this.paged(page = paging.page, pageSize = paging.pageSize)

fun <T> Collection<T>.paged(page: Int, pageSize: Int): List<T> {
    require(page > 0) { "Page must be positive" }
    require(pageSize > 0) { "Page size must be positive" }

    return this
        .drop((page - 1) * pageSize)
        .take(pageSize)
}