package com.example.zappay.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.zappay.remote.RetrofitInstance
import com.example.zappay.viewmodel.UsuarioFormViewModel
import com.example.zappay.request.UsuarioRequest
import kotlinx.coroutines.launch

@Composable
//Se agrego base de datos
fun RegistroScreen(navController: NavController) {
    val viewModel = remember { UsuarioFormViewModel() }
    var mostrarConfirmacion by remember { mutableStateOf(false) }
    val scope= rememberCoroutineScope()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState())

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
            keyboardOptions = KeyboardOptions(
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
            keyboardOptions = KeyboardOptions(
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
        // Campo edad
        OutlinedTextField(
            value = viewModel.edad,
            onValueChange = { viewModel.edad = it },
            label = { Text("Edad") },
            modifier = Modifier.fillMaxWidth(),
            isError = viewModel.errorEdad.isNotBlank(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )

        if (viewModel.errorEdad.isNotBlank()) {
            Text(
                viewModel.errorEdad,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        //Campo contraseña
        OutlinedTextField(
            value = viewModel.password,
            onValueChange = { viewModel.password = it },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            isError = viewModel.errorPassword.isNotBlank(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            )
        )

        if (viewModel.errorPassword.isNotBlank()) {
            Text(
                viewModel.errorPassword,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        //campo rut

        OutlinedTextField(
            value = viewModel.rut,
            onValueChange = { viewModel.rut = it },
            label = { Text("Rut(Con punto y guion)") },
            modifier = Modifier.fillMaxWidth(),
            isError = viewModel.errorRut.isNotBlank(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            )
        )

        if (viewModel.errorRut.isNotBlank()) {
            Text(
                viewModel.errorRut,
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
                    scope.launch{
                        // Registrar usuario
                        val usuarioNuevo= UsuarioRequest(
                            viewModel.id,
                            viewModel.createdAt,
                            viewModel.nombre,
                            viewModel.correo,
                            viewModel.edad,
                            viewModel.password,
                            viewModel.rut,
                            1000.0
                        )
                        RetrofitInstance.api.crearNuevoUsuario(usuarioNuevo)}

                    mostrarConfirmacion = true
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrar Usuario")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón Volver
        Button(
            onClick = {
                // Esto debe llevarte al inicio
                navController.navigate("PaginaInicio") {
                    popUpTo("PaginaInicio") { inclusive = true }
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
                            navController.navigate("PaginaInicio")
                        }
                    ) {
                        Text("Más Tarde")
                    }
                }
            )
        }
    }

}





