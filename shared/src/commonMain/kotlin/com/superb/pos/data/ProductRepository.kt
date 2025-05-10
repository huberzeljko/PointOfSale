package com.superb.pos.data

import com.superb.pos.domain.Product
import kotlinx.coroutines.delay

interface ProductRepository {
    suspend fun findAll(): List<Product>
}

public class FixturesProductRepository : ProductRepository {
    override suspend fun findAll(): List<Product> {
        delay(500)

        return listOf(
            Product(1,  "Coffee",          2.99,  "Beverages",      "Freshly brewed"),
            Product(2,  "Tea",             1.49,  "Beverages"),
            Product(3,  "Water",           0.99,  "Beverages"),
            Product(4,  "Sandwich",        4.99,  "Food",           "Ham and cheese"),
            Product(5,  "Cake",            3.50,  "Dessert"),
            Product(6,  "Bagel",           1.99,  "Bakery",         "Freshly baked"),
            Product(7,  "Muffin",          2.49,  "Bakery",         "Blueberry"),
            Product(8,  "Croissant",       2.99,  "Bakery",         "Butter"),
            Product(9,  "Bread",           2.19,  "Bakery"),
            Product(10, "Yogurt",          0.99,  "Dairy",          "Strawberry"),
            Product(11, "Cheese",          4.99,  "Dairy",          "Cheddar"),
            Product(12, "Milk",            1.99,  "Dairy"),
            Product(13, "Butter",          2.79,  "Dairy"),
            Product(14, "Eggs",            3.49,  "Dairy",          "Dozen"),
            Product(15, "Apple",           0.50,  "Produce"),
            Product(16, "Banana",          0.30,  "Produce"),
            Product(17, "Carrot",          0.20,  "Produce"),
            Product(18, "Broccoli",        1.25,  "Produce"),
            Product(19, "Lettuce",         1.10,  "Produce"),
            Product(20, "Chicken Breast",  5.99,  "Meat",           "Boneless"),
            Product(21, "Ground Beef",     4.99,  "Meat"),
            Product(22, "Pork Chop",       6.49,  "Meat"),
            Product(23, "Salmon Fillet",   8.99,  "Seafood"),
            Product(24, "Shrimp",          7.99,  "Seafood",        "16–20 count"),
            Product(25, "Orange Juice",    3.49,  "Beverages"),
            Product(26, "Soda",            1.29,  "Beverages",      "Can"),
            Product(27, "Smoothie",        4.49,  "Beverages",      "Mixed berry"),
            Product(28, "Rice",            2.99,  "Pantry"),
            Product(29, "Pasta",           1.29,  "Pantry"),
            Product(30, "Cereal",          3.99,  "Pantry"),
            Product(31, "Oatmeal",         2.49,  "Pantry"),
            Product(32, "Peanut Butter",   3.59,  "Pantry"),
            Product(33, "Jam",             2.99,  "Pantry",         "Strawberry"),
            Product(34, "Honey",           4.19,  "Pantry"),
            Product(35, "Granola Bar",     1.25,  "Snacks"),
            Product(36, "Potato Chips",    2.49,  "Snacks",         "Salted"),
            Product(37, "Chocolate Bar",   1.99,  "Snacks",         "Dark chocolate"),
            Product(38, "Cookie",          0.99,  "Snacks",         "Chocolate chip"),
            Product(39, "Cake Pop",        1.49,  "Dessert"),
            Product(40, "Ice Cream",       4.99,  "Frozen",         "Vanilla"),
            Product(41, "Frozen Pizza",    5.99,  "Frozen",         "Pepperoni"),
            Product(42, "Frozen Vegetables",3.49, "Frozen"),
            Product(43, "Dish Soap",       2.99,  "Household"),
            Product(44, "Laundry Detergent",6.99, "Household"),
            Product(45, "Paper Towels",    3.99,  "Household"),
            Product(46, "Toilet Paper",    5.49,  "Household"),
            Product(47, "Trash Bags",      4.29,  "Household"),
            Product(48, "Light Bulbs",     4.99,  "Household"),
            Product(49, "Batteries",       3.99,  "Household"),
            Product(50, "Sponges",         1.49,  "Household"),
            Product(51, "Toothpaste",      2.99,  "Personal Care"),
            Product(52, "Shampoo",         4.49,  "Personal Care"),
            Product(53, "Conditioner",     4.49,  "Personal Care"),
            Product(54, "Hand Soap",       2.49,  "Personal Care"),
            Product(55, "Lotion",          5.99,  "Personal Care")
        )
    }
}