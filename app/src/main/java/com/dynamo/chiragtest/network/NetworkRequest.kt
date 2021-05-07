package com.dynamo.chiragtest.network

import com.dynamo.chiragtest.model.RocketData
import io.reactivex.Observable
import retrofit2.http.GET

interface NetworkRequest {

    @GET("/v4/rockets")
    suspend fun retrieveRepositories(): List<RocketData>
}