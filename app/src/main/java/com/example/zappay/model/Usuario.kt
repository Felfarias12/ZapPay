package com.example.zappay.model

data class Usuario(
    val id: Int,
    var nombre: String,
    var apellido: String,
    var nacionalidad: String,
    var rut: String,
    var correo: String,
    var saldo: Double = 0.0, //
    var faceId: String = ""
) {
    fun mostrarInfo(): String {
        return "ID: $id\nNombre: $nombre $apellido\nNacionalidad: $nacionalidad\nRut: $rut\nCorreo: $correo\nSaldo: $$saldo"
    }

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

