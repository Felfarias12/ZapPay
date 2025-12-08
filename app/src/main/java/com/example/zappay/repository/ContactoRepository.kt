// En FormularioRepository.kt
package com.example.zappay.repository

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.zappay.model.Contacto
import com.example.zappay.remote.RetrofitInstance
import com.example.zappay.request.ContactoRequest


object ContactoRepository {
    private var _contactos by mutableStateOf(listOf<Contacto>())

    val contactos: List<Contacto> get() = _contactos
    private var nextId = 1

    suspend fun crearNuevoContacto (
        id:Int,
        createdAt: Long,
        nombre: String,
        rut: String,
        correo: String,
        saldo: Double = 0.0,
    ): Contacto? {
        val request = ContactoRequest(
            id = id,
            nombre = nombre,
            rut = rut,
            correo = correo,
            saldo = saldo,
            createdAt = createdAt
        )


        return RetrofitInstance.api.crearNuevoContacto(request)

    }
}