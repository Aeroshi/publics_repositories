package com.aeroshi.repositories.views.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.aeroshi.repositories.R
import com.aeroshi.repositories.data.AppDatabase
import com.aeroshi.repositories.data.PublicRepsRepository
import com.aeroshi.repositories.data.entitys.Rep
import com.aeroshi.repositories.databinding.FragmentHomeBinding
import com.aeroshi.repositories.extensions.logDebug
import com.aeroshi.repositories.extensions.logWarning
import com.aeroshi.repositories.extensions.navigate
import com.aeroshi.repositories.util.Executors.Companion.ioThread
import com.aeroshi.repositories.util.InternetUtil.Companion.isOnline
import com.aeroshi.repositories.util.enuns.ErrorType
import com.aeroshi.repositories.viewmodels.HomeViewModel
import com.aeroshi.repositories.viewmodels.MainViewModel
import com.aeroshi.repositories.views.MainActivity
import com.aeroshi.repositories.views.adapters.GitAdapter
import com.google.android.material.snackbar.Snackbar


class HomeFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener,
    GitAdapter.ItemClickListener {

    companion object {
        private const val TAG = "HomeFragment"
    }

    private lateinit var mMainActivity: MainActivity

    private lateinit var mMainViewModel: MainViewModel

    private lateinit var mViewModel: HomeViewModel

    private lateinit var mBinding: FragmentHomeBinding

    private lateinit var mAdapter: GitAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mMainActivity = activity as MainActivity


        if (!isMainViewModelInitialized())
            initializeMainViewModel()

        if (!isViewModelInitialized())
            initializeViewModel()

        if (!isBindingInitialized())
            initializeBinding(inflater, container)

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addViewModelObservers()
    }

    override fun onRefresh() {
        mBinding.swipeRefresh.isRefreshing = false
        if (isOnline(mMainActivity.applicationContext)) {
            mViewModel.mRepositories.value?.clear()
            mAdapter.repositories.clear()
            ioThread {
                mMainActivity.applicationContext.let { context ->

                    val configManagerRepository = PublicRepsRepository.getInstance(
                        AppDatabase.getInstance(context).publicReps()
                    )
                    configManagerRepository.deleteAll()

                    mViewModel.doPublicRepositories(context)

                }
            }
        } else {
            Snackbar.make(
                mBinding.principal,
                R.string.error_no_connection,
                Snackbar.LENGTH_SHORT
            ).show()
        }

    }

    override fun onStop() {
        logDebug(TAG, "onStop")
        super.onStop()
        mViewModel.clearDisposables()
    }


    override fun onItemClick(rep: Rep) {
        mMainViewModel.mSelectedRepository.value = rep
        this.navigate(R.id.navigate_home_to_repositoryDetailsFragment)
    }


    private fun isMainViewModelInitialized() = ::mMainViewModel.isInitialized

    private fun initializeMainViewModel() {
        mMainViewModel = ViewModelProvider(mMainActivity).get(MainViewModel::class.java)
    }

    private fun isViewModelInitialized() = ::mViewModel.isInitialized

    private fun initializeViewModel() {
        mViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    private fun addViewModelObservers() {
        logWarning(TAG, "hasActiveObservers: ${mViewModel.mError.hasActiveObservers()}")
        logWarning(TAG, "hasObservers: ${mViewModel.mError.hasObservers()}")
        mViewModel.mError.observe(viewLifecycleOwner, observeError())
    }

    private fun isBindingInitialized() = ::mBinding.isInitialized

    private fun initializeBinding(inflater: LayoutInflater, container: ViewGroup?) {
        mBinding = FragmentHomeBinding.inflate(inflater, container, false)
        mBinding.lifecycleOwner = this
        mBinding.swipeRefresh.setOnRefreshListener(this)
        mBinding.viewModel = mViewModel
        setAdapter()
        ioThread {
            mMainActivity.applicationContext.let { context ->

                val configManagerRepository = PublicRepsRepository.getInstance(
                    AppDatabase.getInstance(context).publicReps()
                )
                val repositories = configManagerRepository.getPublicReps() as ArrayList<Rep>
                if (repositories.isNullOrEmpty()) {
                    mViewModel.doPublicRepositories(context)
                } else {
                    logDebug(TAG, "reps on db")
                    logDebug(TAG, "size: ${repositories.size}")
                    logDebug(TAG, repositories.toString())
                    mViewModel.mRepositories.postValue(repositories)
                    mViewModel.mError.postValue(ErrorType.NONE)
                    mViewModel.mLoading.postValue(false)
                }
            }
        }
        listeners()
    }

    private fun listeners() {
        mBinding.recycleViewRepositories.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(@NonNull recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) {
                    ioThread {
                        if (mBinding.search.query.isNullOrEmpty() && mViewModel.mLoading.value == false) {
                            mBinding.recycleViewRepositories.stopScroll()
                            mViewModel.doPublicRepositories(mMainActivity.applicationContext)
                        }
                    }
                }
            }
        })


        mBinding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                mAdapter.filter.filter(newText)
                return false
            }

        })


    }


    private fun observeError(): Observer<ErrorType> {
        return Observer {
            if (it == ErrorType.NONE) {
                mAdapter.update(mViewModel.mRepositories.value!!)
            } else {
                Snackbar.make(
                    mBinding.principal,
                    R.string.error_get_repositories,
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun setAdapter() {
        mAdapter = GitAdapter(mMainActivity.applicationContext, this)
        mBinding.recycleViewRepositories.layoutManager =
            LinearLayoutManager(mMainActivity.applicationContext)
        mBinding.recycleViewRepositories.adapter = mAdapter
    }


}