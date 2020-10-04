package com.aeroshi.repositories.data

import androidx.room.TypeConverter
import com.aeroshi.repositories.data.entitys.Owner
import com.google.gson.Gson

class Converters {

    @TypeConverter
    fun ownerToJson(value: Owner): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToOwner(value: String): Owner = Gson().fromJson(value, Owner::class.java)



}
