package com.example.zappay.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.zappay.ui.screens.ContactosScreen
import com.example.zappay.ui.screens.InicioScreen
import com.example.zappay.ui.screens.ListaUsuariosScreen
import com.example.zappay.ui.screens.PagoScreen
import com.example.zappay.ui.screens.RegistroScreen
import com.example.zappay.utils.SessionManager

@Composable
fun Navegacion() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val sessionManager = remember { SessionManager(context) }


    LaunchedEffect(Unit) {
        if (sessionManager.isLoggedIn()) { // al iniciar la app, verifica
            navController.navigate("InicioScreen") { //// si hay sesión, va directo al inicio, lo mismo que el login
                popUpTo("PaginaInicio") { inclusive = true }
            }
        }
    }

    NavHost(navController, startDestination = "PaginaInicio") {
        composable("PaginaInicio") { PaginaInicio(navController) }
        composable("Login") { Login(navController) }
        composable("InicioScreen") { InicioScreen(navController) }
        composable("RegistroScreen") { RegistroScreen(navController) }
        composable("CamaraFotos") { CamaraFotos(navController) }
        composable("PagoScreen") { PagoScreen(navController) }
        composable("ListaUsuariosScreen") { ListaUsuariosScreen(navController) }
        composable("AgregarUsuarios") { ContactosScreen(navController) }
    }
}

