package com.example.zappay

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.zappay.ui.AgregarUsuarios
import com.example.zappay.ui.theme.ZapPayTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ZapPayTheme {
                AgregarUsuarios()

            }
        }
    }
}

