package com.example.a6_2.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.a6_2.domain.entity.NobelPrize
import com.example.a6_2.presentation.detail.PrizeDetailScreen
import com.example.a6_2.presentation.list.PrizeListScreen
import com.example.a6_2.presentation.list.PrizeListViewModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.URLDecoder
import java.net.URLEncoder

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    // Создаём ViewModel без фабрики
    val viewModel: PrizeListViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "prize_list"
    ) {
        composable("prize_list") {
            PrizeListScreen(
                onPrizeClick = { prize ->
                    val json = Json.encodeToString(prize)
                    val encoded = URLEncoder.encode(json, "UTF-8")
                    navController.navigate("prize_detail/$encoded")
                },
                viewModel = viewModel
            )
        }

        composable(
            route = "prize_detail/{prizeJson}",
            arguments = listOf(
                navArgument("prizeJson") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val encoded = backStackEntry.arguments?.getString("prizeJson") ?: return@composable
            val prize = try {
                val json = URLDecoder.decode(encoded, "UTF-8")
                Json.decodeFromString<NobelPrize>(json)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }

            if (prize != null) {
                PrizeDetailScreen(
                    prize = prize,
                    onBack = { navController.popBackStack() }
                )
            } else {
                PrizeDetailScreen(
                    prize = NobelPrize(
                        awardYear = "Unknown",
                        category = "unknown",
                        categoryFullName = "Ошибка загрузки",
                        dateAwarded = null,
                        laureates = emptyList()
                    ),
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}