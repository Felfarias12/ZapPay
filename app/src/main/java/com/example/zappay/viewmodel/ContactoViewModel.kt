
package com.example.zappay.viewmodel

import androidx.lifecycle.ViewModel
import com.example.zappay.model.Contacto
import com.example.zappay.repository.ContactoRepository

// Simple ViewModel to expose the reactive data from the Singleton Repository
class ContactosViewModel : ViewModel() {
    // Expose the list from the repository as a Composable state.
    // This allows the screen to automatically recompose when a new contact is added.
    val contactos: List<Contacto>
        get() = ContactoRepository.contactos

    // Function to add new contact, keeping form logic separate from the UI
    fun addContacto(nombre: String, rut: String, correo: String) {
        ContactoRepository.crearNuevoContacto(
            nombre = nombre,
            rut = rut,
            correo = correo
        )
    }
}