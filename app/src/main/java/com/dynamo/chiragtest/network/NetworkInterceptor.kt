package com.dynamo.chiragtest.network

import android.content.Context
import android.net.ConnectivityManager
import com.dynamo.chiragtest.App
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class NetworkInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isNetworkConnected()) {
            throw  NoConnectivityException();
        }

        val builder: Request.Builder = chain.request().newBuilder();
        return chain.proceed(builder.build());

    }

    private fun isNetworkConnected(): Boolean {
        val connectivityManager =
            App.context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.let {
            val activeNetwork = connectivityManager.activeNetworkInfo
            activeNetwork?.let {
                return activeNetwork.isConnected
            }
        }
        return false
    }
}