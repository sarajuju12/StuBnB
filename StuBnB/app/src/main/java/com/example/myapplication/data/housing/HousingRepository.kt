package com.example.myapplication.data.housing

import com.example.myapplication.models.Housing
import com.example.myapplication.models.Date
import com.google.firebase.database.*
import android.util.Log
import com.example.myapplication.models.Inventory


class HousingRepository : IHousingRepository {

    private val temporaryTestingHousingList: MutableList<Housing> = mutableListOf()

    init {
        // dummy dates
        val startDate = Date(2019, 1, 28)
        val endDate = Date(2024, 2, 4)
        // dummy data
        for (i in 1..3){
            temporaryTestingHousingList.add(
                Housing(
                    i.toString(),
                    "House",
                    "Description for House",
                    mutableListOf("link1a", "link1b"), // links
                    "cs346",
                    14.5,
                    startDate,
                    endDate,
                )
            )
        }
    }

    override fun getHousing(callback: HousingCallback){
        val database = FirebaseDatabase.getInstance()
        val myRef: DatabaseReference = database.getReference("housing")
        val housingList: MutableList<Housing> = mutableListOf()

        // Just get the data once and then stop listening
        myRef.addListenerForSingleValueEvent(

            object : ValueEventListener {

                // dataSnapshot, of exactly at this moment.
                override fun onDataChange(housingSnapshot: DataSnapshot) {
                    //for (userSnapshot in housingSnapshot.children) {
                        val housing = housingSnapshot.getValue(Housing::class.java)
                        housing?.let {
                            housingList.add(it)
                        }
                    //}
                    callback.onHousingLoaded(housingList)
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.e("getHousing", "Failed to get housing items.", databaseError.toException())
                }
            })
    }
    override fun getHousingOfUser(callback: HousingCallback, userID : String){
        val database = FirebaseDatabase.getInstance()
        val myRef: DatabaseReference = database.getReference("housing")
        val housingList: MutableList<Housing> = mutableListOf()

        myRef.child(userID).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(userSnapshot: DataSnapshot) {
                for (housingSnapshot in userSnapshot.children) {
                    val housing = housingSnapshot.getValue(Housing::class.java)
                    housing?.let { housingList.add(it) }
                }

                callback.onHousingLoaded(housingList)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("getHousingOfUser", "Failed to get housing items of user $userID.", databaseError.toException())
            }
        })
    }
    override fun createHousing(newHousingItem : Housing){
        val database = FirebaseDatabase.getInstance()
        val myRef: DatabaseReference = database.getReference("housing")

        myRef.child(newHousingItem.userId).child(newHousingItem.name).setValue(newHousingItem)
    }
}
