package com.example.zappay.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.zappay.repository.UsuarioRepository
import com.example.zappay.viewmodel.UsuarioFormViewModel

@Composable
fun RegistroScreen(navController: NavController) {
    val viewModel = remember { UsuarioFormViewModel() }
    var mostrarConfirmacion by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(
            "Registro de Usuario",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp),
            color = MaterialTheme.colorScheme.onBackground
        )

        // Campo Nombre
        OutlinedTextField(
            value = viewModel.nombre,
            onValueChange = { viewModel.nombre = it },
            label = { Text("Nombre completo") },
            modifier = Modifier.fillMaxWidth(),
            isError = viewModel.errorNombre.isNotBlank(),
            keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                keyboardType = KeyboardType.Text
            )
        )

        if (viewModel.errorNombre.isNotBlank()) {
            Text(
                viewModel.errorNombre,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Campo Correo
        OutlinedTextField(
            value = viewModel.correo,
            onValueChange = { viewModel.correo = it },
            label = { Text("Correo electrónico") },
            modifier = Modifier.fillMaxWidth(),
            isError = viewModel.errorCorreo.isNotBlank(),
            keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                keyboardType = KeyboardType.Email
            )
        )

        if (viewModel.errorCorreo.isNotBlank()) {
            Text(
                viewModel.errorCorreo,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Checkbox Términos
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = viewModel.aceptaTerminos,
                onCheckedChange = { viewModel.aceptaTerminos = it }
            )
            Text(
                "Acepto los términos y condiciones",
                modifier = Modifier.padding(start = 5.dp),
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        if (viewModel.errorTerminos.isNotBlank()) {
            Text(
                viewModel.errorTerminos,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Botón Registrar
        Button(
            onClick = {
                if (viewModel.validarFormulario()) {
                    // Registrar usuario
                    UsuarioRepository.crearNuevoUsuario(
                        nombre = viewModel.nombre,
                        apellido = "", // Podemos dividir el nombre si quieres
                        nacionalidad = "No especificada",
                        telefono = "No especificado",
                        correo = viewModel.correo,
                        saldo = 1000.0 // Saldo inicial para pruebas
                    )
                    mostrarConfirmacion = true
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrar Usuario")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón Volver - ESTE SÍ DEBE FUNCIONAR
        Button(
            onClick = {
                // Esto debe llevarte al inicio
                navController.navigate("InicioScreen") {
                    popUpTo("InicioScreen") { inclusive = true }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
            )
        ) {
            Text("Volver al Inicio")
        }

        // Diálogo de confirmación
        if (mostrarConfirmacion) {
            AlertDialog(
                onDismissRequest = { mostrarConfirmacion = false },
                title = { Text("Registro Exitoso") },
                text = { Text("Usuario registrado correctamente. ¿Quieres configurar el reconocimiento facial ahora?") },
                confirmButton = {
                    Button(
                        onClick = {
                            mostrarConfirmacion = false
                            navController.navigate("CamaraFotos")
                        }
                    ) {
                        Text("Configurar Rostro")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            mostrarConfirmacion = false
                            viewModel.limpiarFormulario()
                            navController.navigate("InicioScreen")
                        }
                    ) {
                        Text("Más Tarde")
                    }
                }
            )
        }
    }
}