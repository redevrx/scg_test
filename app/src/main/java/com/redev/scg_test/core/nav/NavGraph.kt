package com.redev.scg_test.core.nav

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.redev.scg_test.features.news.domain.model.Article
import com.redev.scg_test.features.news.presentation.DetailUI
import com.redev.scg_test.features.news.presentation.NewUI
import kotlinx.serialization.Serializable


val Nav = compositionLocalOf<NavHostController> {
    error("No LocalNavController provided")
}

fun NavController.navigateTo(destination: Destination){
    kotlin.runCatching {
        navigate(destination)
    }.onFailure {
        Log.e("navigation error","not found destination -> error $it")
    }
}

@Serializable
object NewsScreen

@Serializable
object NewDetailScreen

@SuppressLint("NewApi")
@Composable
fun NavigationGraph(){
    NavHost(navController = Nav.current, startDestination = NewsScreen){
        composable<NewsScreen> {
            NewUI()
        }
        composable<NewDetailScreen> {
            DetailUI()
        }
    }
}

