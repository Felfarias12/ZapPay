package com.example.zappay.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.zappay.ui.screens.InicioScreen
import com.example.zappay.ui.screens.RegistroScreen


@Composable
fun Navegacion(){
    val navController= rememberNavController()
    NavHost(navController,startDestination= "inicioscreen"){
        composable("inicioscreen") { InicioScreen(navController) }
        composable("RegistroScreen") { RegistroScreen(navController) }
        composable("CamaraFotos"){ CamaraFotos(navController) }
    }

}
