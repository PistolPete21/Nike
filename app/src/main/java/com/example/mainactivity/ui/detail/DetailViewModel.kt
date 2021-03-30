package com.example.mainactivity.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mainactivity.data.model.Result

class DetailViewModel : ViewModel() {

    private var _likeCount = MutableLiveData<Int>()
    val likeCount: LiveData<Int>
        get() = _likeCount

    private var _intent = MutableLiveData<Result>()
    val intent: LiveData<Result>
        get() = _intent

    var count = 0

    fun launchShare(item: Result) {
        _intent.postValue(item)
    }

    fun increaseLikeCount() {
        count++
        _likeCount.postValue(count)
    }
}