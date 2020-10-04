package com.aeroshi.repositories.views.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.aeroshi.repositories.R
import com.aeroshi.repositories.data.AppDatabase
import com.aeroshi.repositories.data.PublicRepsRepository
import com.aeroshi.repositories.data.entitys.Rep
import com.aeroshi.repositories.databinding.FragmentHomeBinding
import com.aeroshi.repositories.extensions.logDebug
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

    override fun onResume() {
        super.onResume()
        mBinding.search.setQuery("", false)
        mBinding.search.clearFocus()
    }

    override fun onRefresh() {
        if (isOnline(mMainActivity.applicationContext)) {
            mViewModel.mRepositories.value?.clear()
            mAdapter.repositories.clear()
            mBinding.buttonMore.isEnabled = false
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
            mBinding.swipeRefresh.isRefreshing = false
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
        mViewModel.mError.observe(viewLifecycleOwner, observeError())
        mViewModel.mRepositories.observe(viewLifecycleOwner, observeRepositories())
    }

    private fun isBindingInitialized() = ::mBinding.isInitialized

    private fun initializeBinding(inflater: LayoutInflater, container: ViewGroup?) {
        mBinding = FragmentHomeBinding.inflate(inflater, container, false)
        mBinding.lifecycleOwner = this
        mBinding.swipeRefresh.setOnRefreshListener(this)
        startLoading()
        setAdapter()
        ioThread {
            mMainActivity.applicationContext.let { context ->

                val configManagerRepository = PublicRepsRepository.getInstance(
                    AppDatabase.getInstance(context).publicReps()
                )
                val repositories = configManagerRepository.getPublicReps() as ArrayList<Rep>
                if (repositories.isNullOrEmpty()) {
                    mBinding.swipeRefresh.isRefreshing = true
                    mViewModel.doPublicRepositories(context)
                } else {
                    logDebug(TAG, repositories.toString())
                    mViewModel.mRepositories.postValue(repositories)
                }
            }
        }
        listeners()
    }

    private fun listeners() {

        mBinding.buttonMore.setOnClickListener {
            startLoading()
            ioThread {
                mViewModel.doPublicRepositories(mMainActivity.applicationContext)
            }
        }

        mBinding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                mAdapter.filter.filter(newText)
                mBinding.buttonMore.visibility = if (newText.isNullOrEmpty())
                    View.VISIBLE
                else
                    View.GONE

                return false
            }

        })


    }


    private fun observeError(): Observer<ErrorType> {
        return Observer {
            finishLoading()
            if (it != ErrorType.NONE) {
                Snackbar.make(
                    mBinding.principal,
                    R.string.error_get_repositories,
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun observeRepositories(): Observer<ArrayList<Rep>> {
        return Observer {
            mViewModel.mError.value = ErrorType.NONE
            if (it.isNotEmpty()) {
                finishLoading()
                mAdapter.update(it)
            }
        }
    }

    private fun finishLoading() {
        mBinding.swipeRefresh.isRefreshing = false
        mBinding.buttonMore.isEnabled = true
    }

    private fun startLoading() {
        mBinding.swipeRefresh.isRefreshing = true
        mBinding.buttonMore.isEnabled = false
    }


    private fun setAdapter() {
        mAdapter = GitAdapter(mMainActivity.applicationContext, this)
        mBinding.recycleViewRepositories.layoutManager =
            LinearLayoutManager(mMainActivity.applicationContext)
        mBinding.recycleViewRepositories.adapter = mAdapter
    }


}