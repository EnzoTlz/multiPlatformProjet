package com.amonteiro.a03_kmp_mprolead_g1.presentation.viewmodel

import com.amonteiro.a03_kmp_mprolead_g1.data.remote.PhotographerAPI
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class MainViewModelTest {

    // Construit un HttpClient identique a celui declare dans Koin (apiModule).
    private fun buildClient() = HttpClient {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true }, contentType = ContentType.Any)
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 5000
        }
    }

    private fun buildViewModel() = MainViewModel(PhotographerAPI(buildClient()))

    @Test
    fun init_charge_les_fausses_donnees() {
        val vm = buildViewModel()
        // L'init du ViewModel appelle loadFakeData() : on doit avoir 2 photographes.
        assertEquals(2, vm.dataList.value.size)
    }

    @Test
    fun loadFakeData_contient_les_deux_photographes_attendus() {
        val vm = buildViewModel()
        val noms = vm.dataList.value.map { it.stageName }.toSet()
        assertTrue(noms.contains("Bob la Menace"))
        assertTrue(noms.contains("Jean-Claude Flash"))
    }

    @Test
    fun loadFakeData_positionne_runInProgress_et_errorMessage() {
        val vm = buildViewModel()
        vm.loadFakeData(runInProgress = true, errorMessage = "boom")
        assertEquals(true, vm.runInProgress.value)
        assertEquals("boom", vm.errorMessage.value)
        assertEquals(2, vm.dataList.value.size)
    }

    @Test
    fun loadFakeData_valeurs_par_defaut() {
        val vm = buildViewModel()
        vm.loadFakeData()
        assertEquals(false, vm.runInProgress.value)
        assertEquals("", vm.errorMessage.value)
    }

//    @Test
//    fun loadPhotographer_fait_une_vraie_requete() = runBlocking {
//        val api = PhotographerAPI(buildClient())
//        try {
//            val result = api.loadPhotographers()
//            assertTrue(result.isNotEmpty(), "L'API doit renvoyer au moins un photographe")
//        } finally {
//            api.close()
//        }
//    }
}