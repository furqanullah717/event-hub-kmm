package com.codewithfk.eventhub.event.domain.network

import com.codewithfk.eventhub.event.data.response.EventListResponse
import httpClient
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object NetworkHandler {
    private const val API_KEY = "Kv9uInIsbKqAH4kuYUajU4ABjxUKsLzP"
    private const val BASE_URL = "https://app.ticketmaster.com"
    private const val EVENTS_ENDPOINT = "/discovery/v2/events.json"
    private const val EVENT_DETAILS_ENDPOINT = "/discovery/v2/events/"
    private val kTorClient = httpClient() {
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }
    private suspend fun fetchEvents(
        endPoint: String,
        params: Map<String, String>?
    ): EventListResponse {
        val baseUrl = "$BASE_URL$endPoint"
        return  kTorClient.get(baseUrl) {
            params?.forEach {
                parameter(it.key, it.value)
            }
            parameter("apikey", API_KEY)
        }.body()
    }


    suspend fun createEventsUrl(): EventListResponse {
        return fetchEvents(EVENTS_ENDPOINT, null)
    }

}