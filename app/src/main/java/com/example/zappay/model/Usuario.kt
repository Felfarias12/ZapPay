package com.example.zappay.model

data class Usuario(
    val id: Int,
    var nombre: String,
    var contrasenna:String,
    var rut: String,
    var correo: String,
    var saldo: Double = 0.0, //
    var faceId: String = ""
) {

    fun realizarPago(monto: Double): Boolean {
        return if (saldo >= monto) {
            saldo -= monto
            true
        } else {
            false
        }
    }

    fun recargarSaldo(monto: Double) {
        saldo += monto
    }

    fun transferirFondos(destinatario: Usuario, monto: Double): Boolean {
        if (saldo >= monto && monto > 0) {
            saldo -= monto
            destinatario.saldo += monto
            return true
        }
        return false
    }
}

