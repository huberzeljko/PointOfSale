package com.superb.pos.services

import com.superb.pos.data.ProductRepository
import com.superb.pos.domain.Product
import com.superb.pos.shared.models.Paging
import com.superb.pos.shared.models.ProductsFilter
import com.superb.pos.shared.models.ProductsSorter
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ProductServiceTest {
    private val service: ProductService = ProductService(
        repo = TestingRepo()
    )

    @Test
    fun `getProducts with empty filter returns all products sorted by name ascending`() = runBlocking {
        val filter = ProductsFilter(
            searchQuery = "",
            minPrice    = null,
            maxPrice    = null,
            paging      = Paging(page = 1, pageSize = 2)
        )

        val products = service.getProducts(filter, ProductsSorter.default)

        assertEquals(
            listOf(TestingRepo.Products.cake, TestingRepo.Products.coffee),
            products
        )
    }

    @Test
    fun `getProducts filters by searchQuery case-insensitive`() = runBlocking {
        val filter = ProductsFilter(
            searchQuery = "coffee",
            minPrice    = null,
            maxPrice    = null,
            paging      = Paging.default
        )

        val products = service.getProducts(filter, ProductsSorter.default)

        assertEquals(
            listOf(TestingRepo.Products.coffee),
            products
        )
    }

    @Test
    fun `getProducts filters by minPrice and maxPrice`() = runBlocking {
        val filter = ProductsFilter(
            searchQuery = "",
            minPrice    = 1.0,
            maxPrice    = 3.0,
            paging      = Paging.default
        )
        val sorter = ProductsSorter(ProductsSorter.Field.PRICE, descending = false)

        val products = service.getProducts(filter, sorter)

        assertEquals(
            listOf(TestingRepo.Products.tea, TestingRepo.Products.coffee),
            products
        )
    }

    @Test
    fun `getProducts sorts by price descending`() = runBlocking {
        val filter = ProductsFilter(
            searchQuery = "",
            minPrice    = null,
            maxPrice    = null,
            paging      = Paging(page = 1, pageSize = 2)
        )
        val sorter = ProductsSorter(ProductsSorter.Field.PRICE, descending = true)

        val products = service.getProducts(filter, sorter)

        assertEquals(
            expected = listOf(TestingRepo.Products.sandwich, TestingRepo.Products.cake),
            actual = products
        )
    }

    @Test
    fun `getProducts pagination returns distinct pages of correct size`() = runBlocking {
        val pageSize = 2
        val page1Filter = ProductsFilter("", null, null, Paging(1, pageSize))
        val page2Filter = ProductsFilter("", null, null, Paging(2, pageSize))
        val page3Filter = ProductsFilter("", null, null, Paging(3, pageSize))
        val sorter      = ProductsSorter(ProductsSorter.Field.NAME, false)

        val page1 = service.getProducts(page1Filter, sorter)
        val page2 = service.getProducts(page2Filter, sorter)
        val page3 = service.getProducts(page3Filter, sorter)

        assertEquals(pageSize, page1.size, "Page 1 should have $pageSize items")
        assertEquals(pageSize, page2.size, "Page 2 should have $pageSize items")

        assertTrue(page1.intersect(page2).isEmpty(), "Pages must not overlap")

        assertEquals(
            expected = listOf(TestingRepo.Products.cake, TestingRepo.Products.coffee),
            actual = page1
        )

        assertEquals(
            expected = listOf(TestingRepo.Products.sandwich, TestingRepo.Products.tea),
            actual = page2
        )

        assertEquals(
            expected = listOf(TestingRepo.Products.water),
            actual = page3
        )
    }

    @Test
    fun `getProducts with no matching filters returns empty list`() = runBlocking {
        val filter = ProductsFilter("no-such-product", null, null, Paging(1, 10))
        val sorter = ProductsSorter(ProductsSorter.Field.NAME, false)

        val products = service.getProducts(filter, sorter)

        assertTrue(products.isEmpty(), "Expected no products when filter matches none")
    }
}

class TestingRepo : ProductRepository {
    object Products {
        val coffee = Product(1,  "Coffee", 2.99)
        val tea = Product(2,  "Tea", 1.49)
        val water = Product(3,  "Water", 0.99)
        val sandwich = Product(4,  "Sandwich", 4.99)
        val cake = Product(5,  "Cake", 3.50)
    }

    override suspend fun findAll(): List<Product> {
        return listOf(
            Products.water,
            Products.coffee,
            Products.tea,
            Products.sandwich,
            Products.cake
        )
    }
}

