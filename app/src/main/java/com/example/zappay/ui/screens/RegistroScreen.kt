package com.example.zappay.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
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
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background)
    ) {
        Text(
            "Crear Cuenta",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp),
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                // Campo Nombre
                OutlinedTextField(
                    value = viewModel.nombre,
                    onValueChange = { viewModel.nombre = it },
                    label = { Text("Nombre completo") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = viewModel.errorNombre.isNotBlank(),
                    shape = RoundedCornerShape(12.dp),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Nombre"
                        )
                    }
                )

                if (viewModel.errorNombre.isNotBlank()) {
                    Text(
                        viewModel.errorNombre,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 8.dp, top = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Campo Correo
                OutlinedTextField(
                    value = viewModel.correo,
                    onValueChange = { viewModel.correo = it },
                    label = { Text("Correo electronico") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = viewModel.errorCorreo.isNotBlank(),
                    shape = RoundedCornerShape(12.dp),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = "Correo"
                        )
                    },
                    keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                        keyboardType = KeyboardType.Email
                    )
                )

                if (viewModel.errorCorreo.isNotBlank()) {
                    Text(
                        viewModel.errorCorreo,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 8.dp, top = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Campo Edad - SIN ICONO (más simple)
                OutlinedTextField(
                    value = viewModel.edad,
                    onValueChange = { viewModel.edad = it },
                    label = { Text("Edad") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = viewModel.errorEdad.isNotBlank(),
                    shape = RoundedCornerShape(12.dp),
                    placeholder = { Text("Ej: 25") },
                    keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    )
                )

                if (viewModel.errorEdad.isNotBlank()) {
                    Text(
                        viewModel.errorEdad,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 8.dp, top = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Campo Contraseña
                OutlinedTextField(
                    value = viewModel.password,
                    onValueChange = { viewModel.password = it },
                    label = { Text("Contraseña") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = viewModel.errorPassword.isNotBlank(),
                    shape = RoundedCornerShape(12.dp),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "Contraseña"
                        )
                    },
                    keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                        keyboardType = KeyboardType.Password
                    )
                )

                if (viewModel.errorPassword.isNotBlank()) {
                    Text(
                        viewModel.errorPassword,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 8.dp, top = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Campo RUT
                OutlinedTextField(
                    value = viewModel.rut,
                    onValueChange = { viewModel.rut = it },
                    label = { Text("RUT (Con punto y guion)") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = viewModel.errorRut.isNotBlank(),
                    shape = RoundedCornerShape(12.dp),
                    placeholder = { Text("12.345.678-9") },
                    keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    )
                )

                if (viewModel.errorRut.isNotBlank()) {
                    Text(
                        viewModel.errorRut,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 8.dp, top = 4.dp)
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
                        "Acepto los terminos y condiciones",
                        modifier = Modifier.padding(start = 8.dp),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                if (viewModel.errorTerminos.isNotBlank()) {
                    Text(
                        viewModel.errorTerminos,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 8.dp, top = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Botón Registrar
                Button(
                    onClick = {
                        if (viewModel.validarFormulario()) {
                            UsuarioRepository.crearNuevoUsuario(
                                nombre = viewModel.nombre,
                                apellido = "",
                                nacionalidad = "No especificada",
                                rut = viewModel.rut,
                                correo = viewModel.correo,
                                saldo = 1000.0
                            )
                            mostrarConfirmacion = true
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(
                        "Registrar Usuario",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Botón Volver
                Button(
                    onClick = {
                        navController.navigate("InicioScreen") {
                            popUpTo("InicioScreen") { inclusive = true }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                ) {
                    Text("Volver al Inicio")
                }
            }
        }

        // Diálogo de confirmación
        if (mostrarConfirmacion) {
            AlertDialog(
                onDismissRequest = { mostrarConfirmacion = false },
                title = {
                    Text(
                        "Registro Exitoso",
                        fontWeight = FontWeight.Bold
                    )
                },
                text = {
                    Text("Usuario registrado correctamente. ¿Quieres configurar el reconocimiento facial ahora?")
                },
                confirmButton = {
                    Button(
                        onClick = {
                            mostrarConfirmacion = false
                            navController.navigate("CamaraFotos")
                        },
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Configurar Rostro")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            mostrarConfirmacion = false
                            viewModel.limpiarFormulario()
                            navController.navigate("PaginaInicio")
                        }
                    ) {
                        Text("Mas Tarde")
                    }
                }
            )
        }
    }
}
