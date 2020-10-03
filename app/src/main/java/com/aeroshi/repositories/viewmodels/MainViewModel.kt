package com.aeroshi.repositories.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aeroshi.repositories.data.entitys.Rep

class MainViewModel : ViewModel() {
    val mSelectedRepository= MutableLiveData<Rep>()
}