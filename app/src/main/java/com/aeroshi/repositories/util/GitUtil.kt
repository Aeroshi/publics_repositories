package com.aeroshi.repositories.util

import com.aeroshi.repositories.data.entitys.Rep
import com.google.gson.Gson


class GitUtil {
    companion object {
        fun repositoriesJsonParser(json: String): ArrayList<Rep> =
            Gson().fromJson(json, Array<Rep>::class.java).toList() as ArrayList
    }
}