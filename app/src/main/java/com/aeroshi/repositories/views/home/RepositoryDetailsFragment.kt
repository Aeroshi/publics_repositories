package com.aeroshi.repositories.views.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.aeroshi.repositories.R
import com.aeroshi.repositories.databinding.FragmentRepositoryDetailsBinding
import com.aeroshi.repositories.extensions.navigate
import com.aeroshi.repositories.viewmodels.MainViewModel
import com.aeroshi.repositories.views.MainActivity
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso

class RepositoryDetailsFragment : Fragment() {

    companion object {
        private const val TAG = "RepDetailsFragment"
    }

    private lateinit var mMainActivity: MainActivity

    private lateinit var mMainViewModel: MainViewModel

    private lateinit var mBinding: FragmentRepositoryDetailsBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mMainActivity = activity as MainActivity


        if (!isMainViewModelInitialized())
            initializeMainViewModel()

        if (!isBindingInitialized())
            initializeBinding(inflater, container)

        return mBinding.root
    }


    private fun isMainViewModelInitialized() = ::mMainViewModel.isInitialized

    private fun initializeMainViewModel() {
        mMainViewModel = ViewModelProvider(mMainActivity).get(MainViewModel::class.java)
    }


    private fun isBindingInitialized() = ::mBinding.isInitialized

    private fun initializeBinding(inflater: LayoutInflater, container: ViewGroup?) {
        mBinding = FragmentRepositoryDetailsBinding.inflate(inflater, container, false)
        mBinding.lifecycleOwner = this
        try {
            Picasso.get()
                .load(mMainViewModel.mSelectedRepository.value!!.owner.avatarUrl)
                .into(mBinding.imageViewUserName)
            mBinding.imageViewUserName.scaleType = ImageView.ScaleType.CENTER_CROP
            mBinding.textViewRepositoryName.text = mMainViewModel.mSelectedRepository.value!!.name
            mBinding.textViewUsername.text = mMainViewModel.mSelectedRepository.value!!.owner.login
            mBinding.textViewDesc.text = mMainViewModel.mSelectedRepository.value!!.description
        } catch (exception: Exception) {
            Snackbar.make(
                mBinding.principal,
                R.string.error_details_repositories,
                Snackbar.LENGTH_LONG
            ).show()
            navigate(R.id.navigate_repositoryDetailsFragment_to_home)
            Log.e(TAG, "Error on set details of repository: ${exception.localizedMessage}")
            exception.stackTrace
        }
        setupClickEvents()
    }


    private fun setupClickEvents() {

        mBinding.buttonShare.setOnClickListener {
            ShareCompat.IntentBuilder.from(mMainActivity)
                .setType("text/plain")
                .setChooserTitle(mMainActivity.application.getString(R.string.share))
                .setText(mMainViewModel.mSelectedRepository.value!!.htmlUrl)
                .startChooser()
        }

        mBinding.buttonOpen.setOnClickListener {
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse(mMainViewModel.mSelectedRepository.value!!.htmlUrl)
            startActivity(openURL)
        }

    }


}