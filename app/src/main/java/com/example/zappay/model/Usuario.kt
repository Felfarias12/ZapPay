package com.example.zappay.model

data class Usuario(
    val id: Int,
    var nombre: String,
    var apellido: String,
    var nacionalidad: String,
    var telefono: String,
    var correo: String,
    var saldo: Double = 0.0,
    var faceId: String = "" // pongo esto para simular la cara, aun nose como se coloca biometrico
) {
    fun mostrarInfo(): String {
        return "ID: $id\nNombre: $nombre $apellido\nNacionalidad: $nacionalidad\nTeléfono: $telefono\nCorreo: $correo\nSaldo: $$saldo"
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
}