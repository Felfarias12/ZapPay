package com.example.zappay.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.zappay.viewmodel.LoginViewModel
import com.example.zappay.viewmodel.FormularioViewModel
import kotlinx.coroutines.launch


@Composable
fun Login(navController: NavController) {
    val scope = rememberCoroutineScope()
    val viewModel: LoginViewModel = remember { LoginViewModel() }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "Inicio Sesion",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp),
            color = MaterialTheme.colorScheme.onBackground
        )

        // Campo Rut
        OutlinedTextField(
            value = viewModel.rut,
            onValueChange = { viewModel.rut = it },
            label = { Text("Rut (Con punto y Guion)") },
            modifier = Modifier.fillMaxWidth(),
            isError = viewModel.errorRut.isNotBlank(),
            keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
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
        //campo rut
        OutlinedTextField(
            value = viewModel.password,
            onValueChange = { viewModel.password = it },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            isError = viewModel.errorPassword.isNotBlank(),
            keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
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


        Button(
            onClick = {
                scope.launch {

                    // 1) Primero CARGAMOS los usuarios desde la API
                    viewModel.cargarUsuarios()

                    // 2) Luego validamos
                    if (viewModel.validarUsuario()) {
                        navController.navigate("InicioScreen") {
                            popUpTo("InicioScreen") { inclusive = true }
                        }
                    } else {
                        // Puedes mostrar un mensaje usando un Snackbar o un estado
                        println("Usuario o contraseña incorrectos")
                    }
                }
            },
            modifier = Modifier
                .width(250.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text("Iniciar Sesion")
        }


        Spacer(modifier = Modifier.height(10.dp))

        // Botón Volver
        Button(
            onClick = {
                // Esto lleva a la pantalla principal
                navController.navigate("PaginaInicio") {
                    popUpTo("PaginaInicio") { inclusive = true }
                }
            },
            modifier = Modifier.width(250.dp).align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
            )
        ) {
            Text("Cerrar Sesion")
        }

    }
}


