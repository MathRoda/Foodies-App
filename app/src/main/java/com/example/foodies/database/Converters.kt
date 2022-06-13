package com.example.foodies.database

import androidx.room.TypeConverter


class Converters {

    @TypeConverter
    fun fromAny(any: Any?): String {
        if (any == null)
            return ""
        return any as String
    }

    @TypeConverter
    fun toAny(string: String?): Any {
        if (string == null)
            return ""
        return string
    }


}