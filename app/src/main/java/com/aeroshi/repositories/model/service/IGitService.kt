package com.aeroshi.repositories.model.service

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface IGitService {

    @GET("repositories")
    fun getPublicRepositories(@Query("since") offset: Long): Single<String>
}