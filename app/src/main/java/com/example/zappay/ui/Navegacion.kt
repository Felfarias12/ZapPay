package com.example.zappay.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.zappay.ui.screens.CamaraScreen
import com.example.zappay.ui.screens.InicioScreen
import com.example.zappay.ui.screens.ListaUsuariosScreen
import com.example.zappay.ui.screens.PagoScreen
import com.example.zappay.ui.screens.RegistroScreen


@Composable
fun Navegacion() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "PaginaInicio") {
        composable("PaginaInicio") { PaginaInicio(navController) }
        composable("inicioscreen") { InicioScreen(navController) }
        composable("RegistroScreen") { RegistroScreen(navController) }
        composable("CamaraFotos") { CamaraFotos(navController) }
        composable("PagoScreen") { PagoScreen(navController) }
        composable("ListaUsuariosScreen") { ListaUsuariosScreen(navController) }
    }
}
