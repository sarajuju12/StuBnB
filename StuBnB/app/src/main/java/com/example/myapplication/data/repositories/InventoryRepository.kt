package com.example.myapplication.data.repositories


import android.util.Log
import com.example.myapplication.models.Inventory
import com.google.firebase.database.*

class InventoryRepository : IInventoryRepository {

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

        myRef.child(newInventoryItem.userId).child(newInventoryItem.name + "_${newInventoryItem.timeStamp}").setValue(newInventoryItem)
    }
}