package com.example.myapplication.data

import com.example.myapplication.models.*

class InventoryRepository : IInventoryRepository {

    val temporaryTestingInventoryList: MutableList<Inventory> = mutableListOf()

    init {
        repeat(5) {
            temporaryTestingInventoryList.add(
                Inventory(
                    name = "Item $it",
                    description = "Description of Item $it",
                    imageLinks = mutableListOf("link1", "link2", "link3"),
                    price = 100 * it,
                    subject = "Subject $it",
                    category = "Category $it",
                    condition = "Condition $it"
                )
            )
        }
    }

    override fun getInventory() : MutableList<Inventory>{
        return temporaryTestingInventoryList;
    }

    override fun getInventoryOfUser(UserID : Integer): MutableList<Inventory>{
        return temporaryTestingInventoryList;
    }

}