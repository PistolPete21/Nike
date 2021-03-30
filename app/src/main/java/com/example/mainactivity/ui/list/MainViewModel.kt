package com.example.mainactivity.ui.list

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mainactivity.data.NikeApiService
import com.example.mainactivity.data.NikeRepository
import com.example.mainactivity.data.RetrofitInstance
import com.example.mainactivity.data.response.BaseResponse
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainViewModel(context: Context) : ViewModel(), CoroutineScope {

    private val viewModelJob = SupervisorJob()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + viewModelJob

    private var _data = MutableLiveData<List<Any>>()
    val data: LiveData<List<Any>>
        get() = _data

    private var _showErrorView = MutableLiveData<Boolean>()
    val showErrorView: LiveData<Boolean>
        get() = _showErrorView

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    init {
        _isLoading.postValue(true)
        launchLoadData(context)
    }

    private fun launchLoadData(context: Context) {
        launch {
            loadData(context)
        }
    }

    private fun checkInternet(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }

    private suspend fun loadData(context: Context) = withContext(Dispatchers.Default) {
        if (checkInternet(context)) {
            try {
                val nikeApiService: NikeApiService = RetrofitInstance.nikeApiService
                val response: BaseResponse = NikeRepository(nikeApiService).getData()
                when (response.status) {
                    "OK" -> {
                        response.results.let { results ->
                            _isLoading.postValue(false)
                            _data.postValue(results)
                        }
                    }
                    else -> _showErrorView.postValue(true)
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
                _isLoading.postValue(false)
                _showErrorView.postValue(true)
            }
            return@withContext
        } else _showErrorView.postValue(true)

    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
        _data.removeObserver { }
    }
}
