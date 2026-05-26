package com.example.a6_2.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.a6_2.data.AppDependencies
import com.example.a6_2.data.AuthSession
import com.example.a6_2.domain.entity.NobelPrize
import com.example.a6_2.presentation.detail.PrizeDetailScreen
import com.example.a6_2.presentation.favorites.FavoritesScreen
import com.example.a6_2.presentation.favorites.FavoritesViewModel
import com.example.a6_2.presentation.list.PrizeListScreen
import com.example.a6_2.presentation.list.PrizeListViewModel
import com.example.a6_2.presentation.login.LoginScreen
import com.example.a6_2.presentation.login.LoginViewModel
import com.example.a6_2.presentation.profile.ProfileScreen
import com.example.a6_2.presentation.profile.ProfileViewModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.URLDecoder
import java.net.URLEncoder

private const val ROUTE_LOGIN = "login"
private const val ROUTE_PRIZE_LIST = "prize_list"
private const val ROUTE_FAVORITES = "favorites"
private const val ROUTE_PROFILE = "profile"

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val prizeListViewModel: PrizeListViewModel = viewModel()
    val loginViewModel: LoginViewModel = viewModel()
    val favoritesViewModel: FavoritesViewModel = viewModel()
    val profileViewModel: ProfileViewModel = viewModel()

    var startDestination by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        val token = AppDependencies.tokenManager.getToken()
        startDestination = if (token != null) ROUTE_PRIZE_LIST else ROUTE_LOGIN
    }

    LaunchedEffect(Unit) {
        AuthSession.sessionExpired.collect {
            navController.navigate(ROUTE_LOGIN) {
                popUpTo(0) { inclusive = true }
            }
        }
    }

    if (startDestination == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        }
        return
    }

    fun navigateToLogin() {
        navController.navigate(ROUTE_LOGIN) {
            popUpTo(0) { inclusive = true }
        }
    }

    fun navigateToPrizeDetail(prize: NobelPrize) {
        val json = Json.encodeToString(prize)
        val encoded = URLEncoder.encode(json, "UTF-8")
        navController.navigate("prize_detail/$encoded")
    }

    NavHost(
        navController = navController,
        startDestination = startDestination!!
    ) {
        composable(ROUTE_LOGIN) {
            LoginScreen(
                onLoginSuccess = {
                    prizeListViewModel.loadFavoriteIds()
                    navController.navigate(ROUTE_PRIZE_LIST) {
                        popUpTo(ROUTE_LOGIN) { inclusive = true }
                    }
                },
                viewModel = loginViewModel
            )
        }

        composable(ROUTE_PRIZE_LIST) {
            PrizeListScreen(
                onPrizeClick = ::navigateToPrizeDetail,
                onFavoritesClick = { navController.navigate(ROUTE_FAVORITES) },
                onProfileClick = { navController.navigate(ROUTE_PROFILE) },
                viewModel = prizeListViewModel
            )
        }

        composable(ROUTE_FAVORITES) {
            LaunchedEffect(Unit) {
                favoritesViewModel.loadFavorites()
            }
            FavoritesScreen(
                onBack = {
                    prizeListViewModel.loadFavoriteIds()
                    navController.popBackStack()
                },
                onPrizeClick = ::navigateToPrizeDetail,
                viewModel = favoritesViewModel
            )
        }

        composable(ROUTE_PROFILE) {
            LaunchedEffect(Unit) {
                profileViewModel.loadProfile()
            }
            ProfileScreen(
                onBack = { navController.popBackStack() },
                onLogout = ::navigateToLogin,
                viewModel = profileViewModel
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
                        id = 0,
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
