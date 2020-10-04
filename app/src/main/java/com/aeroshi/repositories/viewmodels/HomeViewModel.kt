package com.aeroshi.repositories.viewmodels


import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aeroshi.repositories.data.AppDatabase
import com.aeroshi.repositories.data.PublicRepsRepository
import com.aeroshi.repositories.data.entitys.Rep
import com.aeroshi.repositories.extensions.logError
import com.aeroshi.repositories.model.repository.GitRepository
import com.aeroshi.repositories.util.BaseSchedulerProvider
import com.aeroshi.repositories.util.Executors.Companion.ioThread
import com.aeroshi.repositories.util.GitUtil.Companion.repositoriesJsonParser
import com.aeroshi.repositories.util.SchedulerProvider
import com.aeroshi.repositories.util.enuns.ErrorType
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy


class HomeViewModel(
    private val mRepository: GitRepository = GitRepository(),
    private val mScheduler: BaseSchedulerProvider = SchedulerProvider()
) : ViewModel() {
    companion object {
        private const val TAG = "HomeViewModel"
    }

    private val mCompositeDisposable = CompositeDisposable()

    val mRepositories = MutableLiveData<ArrayList<Rep>>()
    val mError = MutableLiveData<ErrorType>()

    override fun onCleared() {
        super.onCleared()
        clearDisposables()
    }

    fun clearDisposables() = mCompositeDisposable.clear()


    fun doPublicRepositories(context: Context) {
        val since = getSince()
        mCompositeDisposable.add(
            mRepository
                .doPubicRepositories(since)
                .subscribeOn(mScheduler.io())
                .observeOn(mScheduler.ui())
                .subscribeBy(
                    onSuccess = { jsonResult ->
                        try {
                            setResult(repositoriesJsonParser(jsonResult), context)
                        } catch (exception: Exception) {
                            mError.value = ErrorType.PARSER
                            logError(TAG, "Error on parser repositories", exception)
                        }
                    },
                    onError = {
                        mError.value = ErrorType.NETWORK
                        logError(TAG, "Error on get repositories", it)
                    }
                )
        )
    }

    private fun setResult(repositories: ArrayList<Rep>, context: Context) {
        saverRepOnDb(repositories, context)
        val tempList = mRepositories.value ?: arrayListOf()
        tempList.addAll(repositories)
        mRepositories.value = tempList
    }

    private fun saverRepOnDb(reps: ArrayList<Rep>, context: Context) {
        ioThread {
            try {
                val configManagerRepository =
                    PublicRepsRepository.getInstance(AppDatabase.getInstance(context).publicReps())

                configManagerRepository.insertPublicReps(reps)
            } catch (exception: Exception) {
                logError(TAG, "Error on save repositories on db", exception)
            }
        }
    }

     fun getSince(): Long {
        return if (mRepositories.value.isNullOrEmpty())
            0
        else {
            val reps = mRepositories.value!!.sortedByDescending { it.id }
            reps[0].id
        }
    }

}