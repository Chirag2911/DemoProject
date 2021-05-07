package com.dynamo.chiragtest.network

import com.dynamo.chiragtest.model.RocketData
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


open class NetworkManager {
    private val service: NetworkRequest
    private val BASE_URL = "https://api.spacexdata.com"

    init {
        val oktHttpClient = OkHttpClient.Builder()
            .addInterceptor(NetworkInterceptor())
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).client(oktHttpClient.build())
            .build()
        service = retrofit.create(NetworkRequest::class.java)
    }

   public suspend fun getRepositories(): List<RocketData> {
        return service.retrieveRepositories()
    }

}