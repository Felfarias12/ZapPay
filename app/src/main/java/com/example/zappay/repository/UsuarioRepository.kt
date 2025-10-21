// En UsuarioRepository.kt
package com.example.zappay.repository

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.zappay.model.Usuario

object UsuarioRepository {
    private var _usuarios by mutableStateOf(listOf<Usuario>())
    val usuarios: List<Usuario> get() = _usuarios
    private var nextId = 1

    fun crearNuevoUsuario(
        nombre: String,
        apellido: String,
        nacionalidad: String,
        rut: String,
        correo: String,
        saldo: Double = 0.0,
        faceId: String = ""
    ): Usuario? {

        val usuario = Usuario(
            id = nextId++,
            nombre = nombre,
            apellido = apellido,
            nacionalidad = nacionalidad,
            rut = rut,
            correo = correo,
            saldo = saldo,
            faceId = faceId
        )

        _usuarios = _usuarios + usuario
        return usuario
    }
}
