package alex.com.ktorclient

import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.http.*

object HttpKtorClient {

    val json by lazy {
        kotlinx.serialization.json.Json {
            isLenient = true
            ignoreUnknownKeys = true
        }
    }

    val client by lazy {
        HttpClient(createEngine()) {
            install(Logging) {
                level = LogLevel.ALL
                logger = Logger.SIMPLE
            }
            install(JsonFeature) {
                //serializer = KotlinxSerializer()
                serializer = KotlinxSerializer(json)
            }

            defaultRequest {
                host = "di-li.ru"
                //header()
                //parameter()
                url {
                    protocol = URLProtocol.HTTP
                }
            }
        }
    }


    private fun createEngine(): HttpClientEngineFactory<HttpClientEngineConfig> {
        return Android.config {
            connectTimeout = 100_000
            socketTimeout = 100_000
        }
    }
}