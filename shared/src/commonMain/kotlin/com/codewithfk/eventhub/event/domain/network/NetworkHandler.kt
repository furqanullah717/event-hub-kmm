package com.codewithfk.eventhub.event.domain.network

import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url

object NetworkHandler {

    const val BASE_URL = "https://www.eventbriteapi.com/v3"
    private val kTorClient = HttpClient()

    suspend fun get(url: String): String {
        return kTorClient.get(url).toString()
    }

    suspend fun post(url: String, requset: Any): String {
        val builder = HttpRequestBuilder()
        builder.url(url)
        builder.setBody(requset)
        return kTorClient.post(builder).toString()
    }

}