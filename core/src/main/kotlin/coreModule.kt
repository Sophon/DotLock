import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.cio.CIO
import kotlinx.serialization.json.Json
import network.HttpClientFactory
import org.koin.dsl.module

val coreModule = module {
    // KMP: single<HttpClientEngine> { httpClientEngine() }
    // KMP: single<HttpClient> { HttpClientFactory.create(get(), get()) }
    single<HttpClient> { HttpClientFactory.create(CIO.create(), get()) }
    single<Json> { Json { ignoreUnknownKeys = true; prettyPrint = true } }
}

// KMP: expect fun httpClientEngine(): HttpClientEngine