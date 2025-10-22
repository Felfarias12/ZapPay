// File: com/example/zappay/ui/screens/ContactosScreen.kt

package com.example.zappay.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.zappay.model.Contacto
import com.example.zappay.viewmodel.ContactosViewModel

@Composable
fun ContactosScreen(
    navController: NavController,
    viewModel: ContactosViewModel = viewModel() // Use standard ViewModel injection
) {
    // Form fields state (Local UI State)
    var nombre by remember { mutableStateOf("") }
    var rut by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var nacionalidad by remember { mutableStateOf("") } // Not used in the repository function but kept for form consistency

    // Contactos list state (Observed from ViewModel)
    val contactos = viewModel.contactos

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // --- 1. Form Section ---
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    "Agregar Nuevo Contacto",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                OutlinedTextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre") }, modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(value = rut, onValueChange = { rut = it }, label = { Text("Rut") }, modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(value = correo, onValueChange = { correo = it }, label = { Text("Correo") }, modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(8.dp))
                // Retain nacionalidad field even if not passed to the simple repository function
                OutlinedTextField(value = nacionalidad, onValueChange = { nacionalidad = it }, label = { Text("Nacionalidad") }, modifier = Modifier.fillMaxWidth())

                Button(
                    onClick = {
                        if (nombre.isNotBlank() && rut.isNotBlank() && correo.isNotBlank()) {
                            viewModel.addContacto(nombre, rut, correo)
                            // Clear fields
                            nombre = ""
                            rut = ""
                            correo = ""
                            nacionalidad = ""
                        }
                    },
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth(),
                    enabled = nombre.isNotBlank() && rut.isNotBlank() && correo.isNotBlank()
                ) {
                    Text("Guardar Contacto")
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        Divider()
        Spacer(modifier = Modifier.height(16.dp))

        // --- 2. List Section ---
        Text(
            "Contactos Registrados (${contactos.size})",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        if (contactos.isEmpty()) {
            Text(
                "No hay contactos registrados.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(contactos) { contacto ->
                    ContactoItemCard(contacto = contacto)
                }
            }
        }
    }
}

// --- Reusable Component for List Item ---

@Composable
fun ContactoItemCard(contacto: Contacto) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "User Icon",
                modifier = Modifier.size(32.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = contacto.nombre,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = contacto.correo,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Text(
                text = "Saldo: $$${"%.2f".format(contacto.saldo)}",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}