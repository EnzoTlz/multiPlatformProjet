package com.amonteiro.a03_kmp_mprolead_g1

import com.amonteiro.a03_kmp_mprolead_g1.data.remote.PhotographerAPI
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

// Tests dans le code COMMUN (commonTest) : ils s'executent sur toutes les
// plateformes (jvm, android, ios) via les taches jvmTest / testDebugUnitTest / etc.
class CommonTest {

    // -----------------------------------------------------------------
    // TEST 1 : reussit toujours.
    // Sert juste a verifier que la CI execute bien les tests communs
    // et repasse au vert.
    // -----------------------------------------------------------------
    @Test
    fun test_qui_reussit_toujours() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun test_appel_api_avec_cle_du_secret() = runTest {
        val client = HttpClient {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true }, contentType = ContentType.Any)
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 10000
            }
        }
        val api = PhotographerAPI(client)
        try {
            val result = api.loadPhotographers()
            assertTrue(
                result.isNotEmpty(),
                "L'API doit renvoyer des photographes : la cle a bien ete recuperee"
            )
        } finally {
            api.close()
        }
    }
}