package com.example.zappay.ui.screens


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.zappay.repository.UsuarioRepository

@Composable
fun PagoScreen(navController: NavController) {
    var monto by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }
    val usuarios = remember { UsuarioRepository.usuarios }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(
            "Realizar Pago Facial",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        if (usuarios.isEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "No hay usuarios registrados",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { navController.navigate("RegistroScreen") }) {
                        Text("Registrar Primer Usuario")
                    }
                }
            }
            return@Column
        }

        // Simulación de pago facial
        OutlinedTextField(
            value = monto,
            onValueChange = { monto = it },
            label = { Text("Monto a pagar") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                // Simular reconocimiento facial
                val usuario = usuarios.first()
                val montoDouble = monto.toDoubleOrNull() ?: 0.0

                if (usuario.realizarPago(montoDouble)) {
                    mensaje = "✅ Pago exitoso! Se cobró $$montoDouble de tu cuenta."
                } else {
                    mensaje = "❌ Saldo insuficiente. Tu saldo es: $${usuario.saldo}"
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Escanear Rostro y Pagar")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.navigate("camara") },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
        ) {
            Text("Probar Cámara")
        }

        if (mensaje.isNotBlank()) {
            Spacer(modifier = Modifier.height(16.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Text(
                    mensaje,
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Info del usuario (simulado)
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    "Usuario Actual (Simulado):",
                    style = MaterialTheme.typography.labelLarge
                )
                Text("Nombre: ${usuarios.first().nombre}")
                Text("Saldo: $${usuarios.first().saldo}")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver al Inicio")
        }
    }
}