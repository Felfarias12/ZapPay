package com.example.zappay.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.zappay.viewmodel.PaymentViewModel

@Composable
fun PagoScreen(navController: NavController) {

    val vm: PaymentViewModel = viewModel()

    // Cargar usuarios desde API
    LaunchedEffect(Unit) {
        vm.cargarUsuarios()
    }

    val usuarios by vm.usuarios.collectAsState()
    val usuarioActual by vm.usuarioActual.collectAsState()
    val mensaje by vm.mensaje.collectAsState()
    val destinatario by vm.destinatario.collectAsState()

    var montoPago by remember { mutableStateOf("") }
    var montoTransferencia by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState())
    ) {

        // --------------------------
        //       PAGO FACIAL
        // --------------------------

        Text(
            "Realizar Pago Facial",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        if (usuarios.isEmpty()) {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Cargando usuarios o no existen todavía.")
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { navController.navigate("RegistroScreen") }) {
                        Text("Registrar Usuario")
                    }
                }
            }
            return@Column
        }

        OutlinedTextField(
            value = montoPago,
            onValueChange = { montoPago = it },
            label = { Text("Monto a pagar") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                vm.realizarPago(montoPago.toDoubleOrNull() ?: 0.0)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Escanear Rostro y Pagar")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (mensaje.isNotBlank()) {
            Card(modifier = Modifier.fillMaxWidth()) {
                Text(
                    mensaje,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Usuario Actual:")
                Text("Nombre: ${usuarioActual?.Nombre}")
                Text("Saldo: $${usuarioActual?.Saldo}")
            }
        }

        Spacer(modifier = Modifier.height(40.dp))


        // --------------------------
        //     TRANSFERENCIA
        // --------------------------

        Text(
            "Transferencia de Fondos",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        OutlinedTextField(
            value = montoTransferencia,
            onValueChange = { montoTransferencia = it },
            label = { Text("Monto a transferir") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Dropdown destinatario
        Box {
            OutlinedButton(
                onClick = { expanded = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(destinatario?.Nombre ?: "Seleccionar destinatario")
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                usuarios.filter { it != usuarioActual }.forEach { usuario ->
                    DropdownMenuItem(
                        text = { Text(usuario.Nombre) },
                        onClick = {
                            vm.seleccionarDestinatario(usuario)
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                vm.transferirFondos(montoTransferencia.toDoubleOrNull() ?: 0.0)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Transferir Fondos")
        }

        Spacer(modifier = Modifier.height(12.dp))

        usuarioActual?.let {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Usuario Actual: ${it.Nombre}")
                    Text("Saldo: $${it.Saldo}")
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        destinatario?.let {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Destinatario: ${it.Nombre}")
                    Text("Saldo: $${it.Saldo}")
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        TextButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver al inicio")
        }
    }
}
