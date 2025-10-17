package com.example.zappay.ui.screens

import android.Manifest
import android.content.Context
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
    var mensajeError by remember { mutableStateOf("") }
    val context = LocalContext.current

    // Launcher para la cámara - DECLARADO PRIMERO
    val cameraLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        // Simulamos que siempre funciona para el demo
        rostroConfigurado = true
        mensajeError = ""
    }

    // Launcher para permisos de cámara
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            // Si se concedió el permiso, abrir cámara
            abrirCamara(context, cameraLauncher)
        } else {
            mensajeError = "Se necesita permiso de cámara para continuar"
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Configuración de Reconocimiento Facial",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (rostroConfigurado) {
            // Mensaje de éxito
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "✅ ¡Configuración Exitosa!",
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Text(
                        "Tu rostro ha sido registrado para pagos",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    navController.navigate("pago") {
                        popUpTo("inicio") { inclusive = false }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Probar Pago Facial")
            }

        } else {
            // Instrucciones
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "📸 Instrucciones para capturar tu rostro:",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text("• Busca un lugar bien iluminado")
                    Text("• Mira directamente a la cámara")
                    Text("• Quítate lentes o accesorios que oculten tu rostro")
                    Text("• Mantén una expresión neutral")
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Botón principal para cámara
            Button(
                onClick = {
                    // Primero solicitar permiso
                    permissionLauncher.launch(Manifest.permission.CAMERA)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text("Abrir Cámara y Capturar Rostro", style = MaterialTheme.typography.bodyLarge)
            }

            // Mostrar mensaje de error si hay
            if (mensajeError.isNotBlank()) {
                Spacer(modifier = Modifier.height(16.dp))
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        mensajeError,
                        modifier = Modifier.padding(16.dp),
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón alternativo para demo
            TextButton(
                onClick = {
                    rostroConfigurado = true
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Simular Configuración Exitosa (Para Demo)")
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Botón para volver
        Button(
            onClick = {
                navController.navigate("inicio") {
                    popUpTo("inicio") { inclusive = true }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                contentColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        ) {
            Text("Volver al Inicio")
        }
    }
}

// Función mejorada para abrir cámara
private fun abrirCamara(context: Context, launcher: androidx.activity.result.ActivityResultLauncher<Intent>) {
    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

    // Verificar si hay app de cámara disponible
    if (intent.resolveActivity(context.packageManager) != null) {
        launcher.launch(intent)
    } else {
        // Si no hay app de cámara, simular éxito para demo
        // En una app real mostrarías un mensaje de error
    }
}