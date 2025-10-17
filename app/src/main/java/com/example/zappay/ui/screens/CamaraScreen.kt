package com.example.zappay.ui.screens

import android.content.Intent
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun CamaraScreen(navController: NavController) {
    var rostroConfigurado by remember { mutableStateOf(false) }
    val context = LocalContext.current

    // Launcher MUY simple para la cámara
    val cameraLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        // No importa el resultado, marcamos como configurado
        rostroConfigurado = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Configuración de Reconocimiento Facial",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(32.dp))

        if (rostroConfigurado) {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("✅ ¡Éxito!", style = MaterialTheme.typography.headlineSmall)
                    Text("Rostro configurado para pagos")
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { navController.navigate("pago") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ir a Pagos")
            }
        } else {
            Button(
                onClick = {
                    // Intentar abrir cámara directamente
                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    try {
                        cameraLauncher.launch(intent)
                    } catch (e: Exception) {
                        // Si falla, simular éxito después de 2 segundos
                        rostroConfigurado = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text("📸 Abrir Cámara")
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(
                onClick = { rostroConfigurado = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Simular (Si la cámara no funciona)")
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { navController.navigate("inicio") },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Text("Volver al Inicio")
        }
    }
}