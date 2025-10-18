// En FormularioRepository.kt
package com.example.zappay.repository

import com.example.zappay.model.FormularioModel
import com.example.zappay.model.MensajesError

object FormularioRepository {  // Cambia class por object

    private var formulario = FormularioModel()
    private var errores = MensajesError()

    fun getFormulario(): FormularioModel = formulario
    fun getMensajesError(): MensajesError = errores

    fun cambiarNombre(nuevoNombre: String) {
        formulario.nombre = nuevoNombre //
    }

    fun validacionNombre(): Boolean {
        return formulario.nombre.isNotBlank()  //
    }

    fun validacionCorreo(): Boolean {
        return formulario.correo.matches(Regex("^[\\w.-]+@[\\w.-]+\\.\\w+$"))
    }

    fun validacionEdad(): Boolean {
        val edadInt = formulario.edad.toIntOrNull()
        return edadInt != null && edadInt in 0..120
    }

    fun validacionTerminos(): Boolean {
        return formulario.terminos
    }
}