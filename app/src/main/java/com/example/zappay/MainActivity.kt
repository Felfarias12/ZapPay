package com.example.zappay

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.zappay.ui.Navegacion
import com.example.zappay.ui.screens.CamaraScreen
import com.example.zappay.ui.screens.InicioScreen
import com.example.zappay.ui.screens.ListaUsuariosScreen
import com.example.zappay.ui.screens.PagoScreen
import com.example.zappay.ui.screens.RegistroScreen
import com.example.zappay.ui.theme.ZapPayTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ZapPayTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                )
                Navegacion()
            }
        }
    }
}


