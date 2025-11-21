package com.example.zappay.model

data class Contacto(
    val id: Int,
    val nombre: String,
    val rut: String,
    val correo: String,
    var saldo: Double,
){
    fun transferencia(destinatario: Contacto, monto: Double): Boolean {
        if (saldo >= monto && monto > 0) {
            saldo -= monto
            destinatario.saldo += monto
            return true
        }
        return false
    }
}

