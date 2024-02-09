package com.example.myapplication.data

import com.example.myapplication.models.Housing
import com.example.myapplication.data.IHousingRepository

class HousingRepository : IHousingRepository {

    override val temporaryTestingHousingList: MutableList<Housing> = mutableListOf()

    init {
        temporaryTestingHousingList.add(
            Housing(
                name = "House",
                description = "Description for House",
                imageLinks = mutableListOf("link1a", "link1b") // links
            )
        )

        temporaryTestingHousingList.add(   // member function
            Housing(
                name = "Table",
                description = "Description for Table",
                imageLinks = mutableListOf("link2a", "link2b", "link2c")
            )
        )
    }

    override fun getHousing() : MutableList<Housing>{
        return temporaryTestingHousingList;
    }

    override fun getHousingOfUser(UserID : Integer): MutableList<Housing>{
        return temporaryTestingHousingList;
    }

}
