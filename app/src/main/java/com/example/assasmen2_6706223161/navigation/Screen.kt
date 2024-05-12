package com.example.assasmen2_6706223161.navigation

sealed class Screen(val route: String) {
    data object Pesan: Screen("pesanScreen")
    data object About: Screen("aboutScreen")
    data object Katalog: Screen("mainScreen")
    data object Desc: Screen("descScreen")
    object FormBaru: Screen("detailScreen")
    object FormUbah : Screen("detailScreen/{id}") {
        fun withId(id: Long) = "detailScreen/$id"
    }
}