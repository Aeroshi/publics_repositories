package com.aeroshi.repositories.model.repository

import com.aeroshi.repositories.model.NetworkAPI
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class GitRepository {

    fun doPubicRepositories(since: Long): Single<String> {
        return NetworkAPI.getGitService()
            .getPublicRepositories(since)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}