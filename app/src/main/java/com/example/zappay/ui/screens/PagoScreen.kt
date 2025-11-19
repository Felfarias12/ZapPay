package com.example.zappay.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.zappay.model.Usuario
import com.example.zappay.repository.ContactoRepository
import com.example.zappay.repository.UsuarioRepository

@Composable
fun PagoScreen(navController: NavController) {
    var monto by remember { mutableStateOf("") }
    var montoTransferencia by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }
    val usuarios = remember { UsuarioRepository.usuarios }
    val usuarioActual = usuarios.firstOrNull()

    // Estado para transferencia
    var expanded by remember { mutableStateOf(false) }
    var destinatarioSeleccionado by remember { mutableStateOf<Usuario?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background)
    ) {
        Text(
            "Realizar Pagos",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp),
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold
        )

        if (usuarios.isEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Sin usuarios",
                        modifier = Modifier.size(48.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "No hay usuarios registrados",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { navController.navigate("RegistroScreen") },
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Registrar Primer Usuario")
                    }
                }
            }
            return@Column
        }

        // Seccion de Pago Facial
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    "Pago Facial",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = monto,
                    onValueChange = { monto = it },
                    label = { Text("Monto a pagar") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    placeholder = { Text("Ej: 10000") }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        val usuario = usuarios.first()
                        val montoDouble = monto.toDoubleOrNull() ?: 0.0

                        if (usuario.realizarPago(montoDouble)) {
                            mensaje = "Pago exitoso! Se cobro $$montoDouble de tu cuenta."
                        } else {
                            mensaje = "Saldo insuficiente. Tu saldo es: $${usuario.saldo}"
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Face,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Escanear Rostro y Pagar")
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = { navController.navigate("camara") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text("Probar Camara")
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Seccion de Transferencia
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    "Transferencia",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                if (ContactoRepository.contactos.isEmpty()) {
                    Text(
                        "Debe haber al menos 2 usuarios registrados para transferir fondos.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = { navController.navigate("AgregarUsuarios") },
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Agregar contacto")
                    }
                } else {
                    OutlinedTextField(
                        value = montoTransferencia,
                        onValueChange = { montoTransferencia = it },
                        label = { Text("Monto a transferir") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        placeholder = { Text("Ej: 5000") }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Seleccion de destinatario
                    Box {
                        OutlinedButton(
                            onClick = { expanded = true },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
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

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            val montoDouble = montoTransferencia.toDoubleOrNull() ?: 0.0

                            if (usuarioActual != null && destinatarioSeleccionado != null) {
                                val exito = usuarioActual.transferirFondos(destinatarioSeleccionado!!, montoDouble)
                                mensaje = if (exito) {
                                    "Transferencia exitosa de $$montoDouble a ${destinatarioSeleccionado!!.nombre}"
                                } else {
                                    "Saldo insuficiente. Tu saldo actual: $${usuarioActual.saldo}"
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.tertiary
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Transferir Fondos")
                    }
                }
            }
        }

        // Mensaje de resultado
        if (mensaje.isNotBlank()) {
            Spacer(modifier = Modifier.height(16.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    mensaje,
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Informacion del usuario actual
        usuarioActual?.let {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        "Tu Informacion",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Nombre: ${it.nombre}")
                    Text("Saldo: $${it.saldo}")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Volver al Inicio")
        }
    }
}