package com.example.myapplication.data.repositories

import com.example.myapplication.models.Housing
import com.example.myapplication.models.Date

class HousingRepository : IHousingRepository {

    override val temporaryTestingHousingList: MutableList<Housing> = mutableListOf()

    init {

        // dummy dates
        val startDate = Date(2019, 1, 28)
        val endDate = Date(2024, 2, 4)

        // dummy data
        for (i in 1..10){
            temporaryTestingHousingList.add(
                Housing(
                    name = "House",
                    description = "Description for House",
                    imageLinks = mutableListOf("link1a", "link1b"), // links

                    seller = "cs346",
                    price = 14,
                    startDate = startDate,
                    endDate = endDate,
                )
            )

            temporaryTestingHousingList.add(   // member function
                Housing(
                    name = "House2",
                    description = "Description for House2",
                    imageLinks = mutableListOf("link2a", "link2b", "link2c"),

                    seller = "cs346",
                    price = 14,
                    startDate = startDate,
                    endDate = endDate,
                )
            )
        }


    }

    override fun getHousing() : MutableList<Housing>{
        return temporaryTestingHousingList;
    }

    override fun getHousingOfUser(UserID : Integer): MutableList<Housing>{
        return temporaryTestingHousingList;
    }
}
