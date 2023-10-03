package com.codewithfk.eventhub.event.domain.network

import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse

object NetworkHandler {
    private const val API_KEY = "apikey=Kv9uInIsbKqAH4kuYUajU4ABjxUKsLzP"
    private const val BASE_URL = "https://app.ticketmaster.com"
    private const val EVENTS_ENDPOINT = "/discovery/v2/events.json"
    private const val EVENT_DETAILS_ENDPOINT = "/discovery/v2/events/"
    private val kTorClient = HttpClient()

    suspend fun get(url: String): HttpResponse {
        return kTorClient.get(url)
    }

    suspend fun post(url: String, requset: Any): String {
        val builder = HttpRequestBuilder()
        builder.url(url)
        builder.setBody(requset)
        return kTorClient.post(builder).toString()
    }

    private fun createUrl(endPoint: String, params: Map<String, String>?): String {
        var baseUrl = "$BASE_URL$endPoint?$API_KEY"
        params?.forEach {
            baseUrl += "&${it.key}=${it.value}"
        }
        return baseUrl
    }


    fun createEventsUrl(): String {
        return createUrl(EVENTS_ENDPOINT, null)
    }

    fun createEventDetailsUrl(eventId: String): String {
        return createUrl("$EVENT_DETAILS_ENDPOINT$eventId", null)
    }

}