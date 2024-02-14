package com.example.myapplication.data

import android.util.Log
import com.example.myapplication.models.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class InventoryRepository : IInventoryRepository {

    val temporaryTestingInventoryList: MutableList<Inventory> = mutableListOf()

    init {
        repeat(5) {
            temporaryTestingInventoryList.add(
                Inventory(
                    name = "Item $it",
                    userId = "",
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

    override fun getInventory(callback: InventoryCallback){
        val database = FirebaseDatabase.getInstance()
        val myRef: DatabaseReference = database.getReference("inventory")
        val inventoryList: MutableList<Inventory> = mutableListOf()

        // Just get the data once and then stop listening
        myRef.addListenerForSingleValueEvent(object : ValueEventListener {

            // dataSnapshot, of exactly at this moment.
            override fun onDataChange(inventorySnapshot: DataSnapshot) {
                // go through all of the children of the dataSnapshot
                for (userSnapshot in inventorySnapshot.children) {
                    for (inventorySnapshot in userSnapshot.children) {
                        val inventory = inventorySnapshot.getValue(Inventory::class.java)
                        inventory?.let {
                            inventoryList.add(it)
                        }
                    }
                }

                callback.onInventoryLoaded(inventoryList)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("getInventory", "Failed to get inventory items.", databaseError.toException())
            }
        })
    }

    override fun getInventoryOfUser(callback: InventoryCallback, userID : String){
        val database = FirebaseDatabase.getInstance()
        val myRef: DatabaseReference = database.getReference("inventory")
        val inventoryList: MutableList<Inventory> = mutableListOf()

        myRef.child(userID).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(userSnapshot: DataSnapshot) {
                for (inventorySnapshot in userSnapshot.children) {
                    val inventory = inventorySnapshot.getValue(Inventory::class.java)
                    inventory?.let { inventoryList.add(it) }
                }

                callback.onInventoryLoaded(inventoryList)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("getInventoryOfUser", "Failed to get inventory items of user $userID.", databaseError.toException())
            }
        })
    }

    override fun createInventory(newInventoryItem : Inventory){
        val database = FirebaseDatabase.getInstance()
        val myRef: DatabaseReference = database.getReference("inventory")

        myRef.child(newInventoryItem.userId).child(newInventoryItem.name).setValue(newInventoryItem)
    }
}