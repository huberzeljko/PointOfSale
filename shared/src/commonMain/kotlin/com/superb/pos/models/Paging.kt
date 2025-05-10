package com.superb.pos.shared.models

data class Paging(val page: Int = 1, val pageSize: Int = 10) {
    companion object {
        val default = Paging(page = 1, pageSize = 20)
    }
}