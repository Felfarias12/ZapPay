package com.example.zappay.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zappay.remote.ApiService
import com.example.zappay.remote.RetrofitInstance
import com.example.zappay.request.UsuarioRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PaymentViewModel : ViewModel() {

    private val api: ApiService = RetrofitInstance.api

    // Lista de usuarios desde API
    private val _usuarios = MutableStateFlow<List<UsuarioRequest>>(emptyList())
    val usuarios: StateFlow<List<UsuarioRequest>> get() = _usuarios

    // Usuario logueado (temporalmente el primero)
    private val _usuarioActual = MutableStateFlow<UsuarioRequest?>(null)
    val usuarioActual: StateFlow<UsuarioRequest?> get() = _usuarioActual

    // Mensaje de error o éxito
    private val _mensaje = MutableStateFlow("")
    val mensaje: StateFlow<String> get() = _mensaje

    // Destinatario transferencia
    private val _destinatario = MutableStateFlow<UsuarioRequest?>(null)
    val destinatario: StateFlow<UsuarioRequest?> get() = _destinatario

    // -----------------------------
    // CARGAR USUARIOS DESDE LA API
    // -----------------------------
    fun cargarUsuarios() {
        viewModelScope.launch {
            try {
                val lista = api.getUsuarios()
                _usuarios.value = lista

                if (lista.isNotEmpty()) {
                    _usuarioActual.value = lista.first()
                }

            } catch (e: Exception) {
                _mensaje.value = "Error cargando usuarios: ${e.localizedMessage}"
            }
        }
    }

    // -----------------------------
    // SELECCIONAR DESTINATARIO
    // -----------------------------
    fun seleccionarDestinatario(usuario: UsuarioRequest) {
        _destinatario.value = usuario
    }

    // -----------------------------
    // PAGAR
    // -----------------------------
    fun realizarPago(monto: Double) {
        val user = _usuarioActual.value ?: return

        if (monto <= 0) {
            _mensaje.value = "El monto debe ser mayor a 0"
            return
        }

        if (user.Saldo >= monto) {
            user.Saldo -= monto
            _mensaje.value = "Pago exitoso. Se descontó $$monto"
        } else {
            _mensaje.value = "Saldo insuficiente. Tu saldo es: $${user.Saldo}"
        }
    }

    // -----------------------------
    // TRANSFERIR
    // -----------------------------
    fun transferirFondos(monto: Double) {
        val user = _usuarioActual.value
        val dest = _destinatario.value

        if (user == null || dest == null) {
            _mensaje.value = "Selecciona un destinatario"
            return
        }

        if (monto <= 0) {
            _mensaje.value = "El monto debe ser mayor a 0"
            return
        }

        if (user.Saldo >= monto) {
            user.Saldo -= monto
            dest.Saldo += monto
            _mensaje.value = "Transferencia de $$monto a ${dest.Nombre} realizada con éxito"
        } else {
            _mensaje.value = "Saldo insuficiente. Tu saldo es: ${user.Saldo}"
        }
    }
}
