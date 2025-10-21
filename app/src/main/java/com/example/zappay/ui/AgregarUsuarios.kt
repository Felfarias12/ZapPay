package com.example.zappay.ui



import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.zappay.repository.UsuarioRepository

@Composable
fun AgregarUsuarios(navController: NavController) {
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var nacionalidad by remember { mutableStateOf("") }
    var rut by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    val usuarios by remember { mutableStateOf(UsuarioRepository.usuarios) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,  // Cambiado a Top para mejor distribución
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Formulario
        Text(
            "Agregar Nuevo Usuario",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = apellido,
            onValueChange = { apellido = it },
            label = { Text("Apellido") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = nacionalidad,
            onValueChange = { nacionalidad = it },
            label = { Text("Nacionalidad") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = rut,
            onValueChange = { rut = it },
            label = { Text("Rut") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = correo,
            onValueChange = { correo = it },
            label = { Text("Correo") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                if (nombre.isNotBlank() && apellido.isNotBlank() && correo.isNotBlank()) {
                    UsuarioRepository.crearNuevoUsuario(
                        nombre = nombre,
                        apellido = apellido,
                        nacionalidad = nacionalidad,
                        rut = rut,
                        correo = correo
                        // saldo y faceId
                    )

                    nombre = ""
                    apellido = ""
                    nacionalidad = ""
                    rut = ""
                    correo = ""
                }
            },
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
        ) {
            Text("Guardar Usuario")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Lista de usuarios
        Text(
            "Usuarios Registrados",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        if (usuarios.isEmpty()) {
            Text(
                "No hay usuarios registrados",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                // Header de la tabla
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Nombre", style = MaterialTheme.typography.labelLarge, modifier = Modifier.weight(1f))
                        Text("Apellido", style = MaterialTheme.typography.labelLarge, modifier = Modifier.weight(1f))
                        Text("Correo", style = MaterialTheme.typography.labelLarge, modifier = Modifier.weight(1.5f))
                    }
                    Divider(thickness = 1.dp)
                }

                // Items de usuarios
                items(usuarios) { usuario ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            usuario.nombre,
                            modifier = Modifier.weight(1f),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            usuario.apellido,
                            modifier = Modifier.weight(1f),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            usuario.correo,
                            modifier = Modifier.weight(1.5f),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                    Divider(thickness = 0.5.dp)
                }
            }
        }
    }
}