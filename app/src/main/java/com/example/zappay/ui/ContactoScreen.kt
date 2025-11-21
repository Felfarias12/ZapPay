// File: com/example/zappay/ui/screens/ContactosScreen.kt

package com.example.zappay.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.zappay.model.Contacto
import com.example.zappay.viewmodel.ContactoFormViewModel
import com.example.zappay.viewmodel.ContactosViewModel



@Composable
fun ContactosScreen(navController: NavController,
    viewModel: ContactosViewModel = viewModel(),
    formViewModel: ContactoFormViewModel = viewModel()
) {


    val contactos = viewModel.contactos

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
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

                OutlinedTextField(
                    value = formViewModel.nombre,
                    onValueChange = { formViewModel.nombre = it },
                    label = { Text("Nombre") },
                    modifier = Modifier.fillMaxWidth()
                )
                if (formViewModel.errorNombre.isNotEmpty()) {
                    Text(formViewModel.errorNombre, color = MaterialTheme.colorScheme.error)
                }
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = formViewModel.rut,
                    onValueChange = { formViewModel.rut = it },
                    label = { Text("Rut") },
                    modifier = Modifier.fillMaxWidth()
                )
                if (formViewModel.errorRut.isNotEmpty()) {
                    Text(formViewModel.errorRut, color = MaterialTheme.colorScheme.error)
                }
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = formViewModel.correo,
                    onValueChange = { formViewModel.correo = it },
                    label = { Text("Correo") },
                    modifier = Modifier.fillMaxWidth()
                )
                if (formViewModel.errorCorreo.isNotEmpty()) {
                    Text(formViewModel.errorCorreo, color = MaterialTheme.colorScheme.error)
                }
                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        if (formViewModel.ValidarContacto()) {
                            viewModel.addContacto(
                                formViewModel.nombre,
                                formViewModel.rut,
                                formViewModel.correo
                            )
                            formViewModel.nombre = ""
                            formViewModel.rut = ""
                            formViewModel.correo = ""
                        }
                    },
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth()
                ) {
                    Text("Guardar Contacto")
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        Divider()
        Spacer(modifier = Modifier.height(16.dp))

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
                text = "Saldo: $${"%.2f".format(contacto.saldo)}",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}