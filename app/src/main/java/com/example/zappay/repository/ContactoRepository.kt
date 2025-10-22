// En FormularioRepository.kt
package com.example.zappay.repository

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.zappay.model.Contacto


object ContactoRepository {
    private var _contactos by mutableStateOf(listOf<Contacto>())

    val contactos: List<Contacto> get() = _contactos
    private var nextId = 1

    fun crearNuevoContacto (
        nombre: String,
        rut: String,
        correo: String,
        saldo: Double = 0.0,
    ): Contacto? {
        val contacto = Contacto(
            id = nextId++,
            nombre = nombre,
            rut = rut,
            correo = correo,
            saldo = saldo
        )

        _contactos = _contactos + contacto
        return contacto
    }
}