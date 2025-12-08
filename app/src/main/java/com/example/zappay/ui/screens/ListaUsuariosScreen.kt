package com.example.zappay.ui.screens


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.zappay.repository.UsuarioRepository
import com.example.zappay.viewmodel.LoginViewModel

@Composable

fun ListaUsuariosScreen(navController: NavController) {

    val viewModel: LoginViewModel = androidx.lifecycle.viewmodel.compose.viewModel()

    val usuarios = viewModel.usuarios


    // Cargar usuarios una sola vez
    LaunchedEffect(Unit) {
        viewModel.cargarUsuarios()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            "Usuarios Registrados",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                "Usuarios Registrados",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp),
                color = MaterialTheme.colorScheme.onBackground
            )

            if (usuarios.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            "No hay usuarios registrados",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { navController.navigate("RegistroScreen") }) {
                            Text("Registrar Usuario")
                        }
                        TextButton(
                            onClick = { navController.popBackStack() },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Volver al inicio")
                        }
                    }
                }

            }else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(usuarios) { usuario ->
                        Card(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    usuario.nombre,
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text("Correo: ${usuario.correo}")
                                Text("Rut: ${usuario.rut}")
                                Text("Edad: ${usuario.edad}")
                                Text("Saldo: ${usuario.saldo}")
                            }
                        }
                    }
                }
            }
        }
    }
}

