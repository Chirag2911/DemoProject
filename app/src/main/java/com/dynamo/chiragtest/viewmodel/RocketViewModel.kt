package com.dynamo.chiragtest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dynamo.chiragtest.model.RocketData
import com.dynamo.chiragtest.network.CallResponseStatus
import com.dynamo.chiragtest.network.NetworkManager
import kotlinx.coroutines.*

class RocketViewModel : ViewModel() {
    private val rocketMutableLiveData = MutableLiveData<CallResponseStatus<List<RocketData>>>()
    private var networkManager:NetworkManager?=null

    init {
        networkManager= NetworkManager()
    }

    fun getRocketList() {
        val mainActivityJob = Job()
        val errorHandler = CoroutineExceptionHandler { _, exception ->
            rocketMutableLiveData.value = CallResponseStatus.error(exception)

        }
        val coroutineScope = CoroutineScope(mainActivityJob as CompletableJob + Dispatchers.Main)
        coroutineScope.launch(errorHandler) {
            networkManager?.getRepositories().let {
                rocketMutableLiveData.value = CallResponseStatus.success(it)
            }
        }
    }

    fun getRockets(): LiveData<CallResponseStatus<List<RocketData>>> {
        return rocketMutableLiveData
    }

}