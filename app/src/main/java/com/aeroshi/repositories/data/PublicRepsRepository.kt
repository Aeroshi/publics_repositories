package com.aeroshi.repositories.data

import com.aeroshi.repositories.data.entitys.Rep

class PublicRepsRepository private constructor(private val publicRepsDao: PublicReps) {

    fun getPublicReps() = publicRepsDao.getPublicReps()

    fun insertPublicReps(reps: ArrayList<Rep>) = publicRepsDao.insertData(reps)

    fun deleteAll() = publicRepsDao.deleteAll()

    companion object {

        @Volatile
        private var instance: PublicRepsRepository? = null

        fun getInstance(publicRepsDao: PublicReps) =
            instance
                ?: synchronized(this) {
                    instance
                        ?: PublicRepsRepository(
                            publicRepsDao
                        ).also { instance = it }
                }


    }
}