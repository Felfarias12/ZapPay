package com.example.zappay.ui.screens


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.zappay.model.Usuario
import com.example.zappay.repository.UsuarioRepository

@Composable
fun PagoScreen(navController: NavController) {
    var monto by remember { mutableStateOf("") }
    var montoT by remember { mutableStateOf("") }

    var mensaje by remember { mutableStateOf("") }
    val usuarios = remember { UsuarioRepository.usuarios }
    // Asumimos el primer usuario como "usuario actual"
    val usuarioActual = usuarios.firstOrNull()

    // Estado para la transferencia
    var expanded by remember { mutableStateOf(false) }
    var destinatarioSeleccionado by remember { mutableStateOf<Usuario?>(null) }

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
                    mensaje = "Pago exitoso! Se cobró $$montoDouble de tu cuenta."
                } else {
                    mensaje = "Saldo insuficiente. Tu saldo es: $${usuario.saldo}"
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

//transferencia
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(
            "Transferencia de Fondos",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        if (usuarios.size < 2) {
            Text("Debe haber al menos 2 usuarios registrados para transferir fondos.")
            Button(onClick = { navController.navigate("RegistroScreen") }) {
                Text("Registrar otro usuario")
            }
            return@Column
        }

        OutlinedTextField(
            value = montoT,
            onValueChange = { montoT = it },
            label = { Text("Monto a transferir") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Selección de destinatario
        Box {
            OutlinedButton(
                onClick = { expanded = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(destinatarioSeleccionado?.nombre ?: "Seleccionar destinatario")
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                usuarios
                    .filter { it != usuarioActual }
                    .forEach { usuario ->
                        DropdownMenuItem(
                            text = { Text(usuario.nombre) },
                            onClick = {
                                destinatarioSeleccionado = usuario
                                expanded = false
                            }
                        )
                    }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                val montoDouble = montoT.toDoubleOrNull() ?: 0.0
                if (usuarioActual != null && destinatarioSeleccionado != null) {
                    val exito = usuarioActual.transferirFondos(destinatarioSeleccionado!!, montoDouble)
                    mensaje = if (exito) {
                        "Transferencia exitosa de $$montoDouble a ${destinatarioSeleccionado!!.nombre}"
                    } else {
                        "Saldo insuficiente o monto inválido. Tu saldo actual: $${usuarioActual.saldo}"
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Transferir Fondos")
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

        Spacer(modifier = Modifier.height(12.dp))

        // Mostrar información del usuario actual
        usuarioActual?.let {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Usuario actual: ${it.nombre}")
                    Text("Saldo: $${it.saldo}")
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Mostrar información del destinatario seleccionado
        destinatarioSeleccionado?.let {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Destinatario: ${it.nombre}")
                    Text("Saldo: $${it.saldo}")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver al inicio")
        }
    }
}
}
