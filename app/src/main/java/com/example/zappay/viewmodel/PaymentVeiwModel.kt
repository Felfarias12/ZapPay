package com.example.zappay.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zappay.remote.ApiService
import com.example.zappay.remote.RetrofitInstance
import com.example.zappay.request.ContactoRequest
import com.example.zappay.request.TransferenciaRequest
import com.example.zappay.request.UsuarioRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class PaymentViewModel : ViewModel() {

    private val api: ApiService = RetrofitInstance.api

    // Lista de usuarios desde API
    private val _usuarios = MutableStateFlow<List<UsuarioRequest>>(emptyList())
    private val _contactos= MutableStateFlow<List<ContactoRequest>>(emptyList())
    val usuarios: StateFlow<List<UsuarioRequest>> get() = _usuarios
    val contactos: StateFlow<List<ContactoRequest>> get() = _contactos

    // Usuario logueado (temporalmente el primero)
    private val _usuarioActual = MutableStateFlow<UsuarioRequest?>(null)
    val usuarioActual: StateFlow<UsuarioRequest?> get() = _usuarioActual

    private val _contactosActual= MutableStateFlow<ContactoRequest?>(null)
    val contactoActual: StateFlow<ContactoRequest?> get()= _contactosActual


    // Mensaje de error o éxito
    private val _mensaje = MutableStateFlow("")
    val mensaje: StateFlow<String> get() = _mensaje

    // Destinatario transferencia
    private val _destinatario = MutableStateFlow<ContactoRequest?>(null)
    val destinatario: StateFlow<ContactoRequest?> get() = _destinatario

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

    fun cargarContacto(){
        viewModelScope.launch {
            try {
                val lista2=api.getContactos()
                _contactos.value=lista2

                if (lista2.isNotEmpty()){
                    _contactosActual.value=lista2.first()

                }
            }catch (e: Exception){
                _mensaje.value="Error cargando contacto: ${e.localizedMessage}"
            }
        }

    }

    // -----------------------------
    // SELECCIONAR DESTINATARIO
    // -----------------------------
    fun seleccionarDestinatario(contacto: ContactoRequest) {
        _destinatario.value = contacto
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

        if (user.saldo >= monto) {
            user.saldo -= monto
            _mensaje.value = "Pago exitoso. Se descontó $$monto"
        } else {
            _mensaje.value = "Saldo insuficiente. Tu saldo es: $${user.saldo}"
        }
    }

    // -----------------------------
    // TRANSFERIR
    // -----------------------------
    fun transferirFondosAPI(monto: Double) {
        val user = _usuarioActual.value ?: return
        val dest = _destinatario.value ?: return

        viewModelScope.launch {
            try {
                val body = TransferenciaRequest(
                    idOrigen = user.id,
                    idDestino = dest.id,
                    monto = monto
                )

                val response = api.transferirFondos(body)

                // Actualizar modelos locales
                _usuarioActual.value = response.usuarioOrigen
                _destinatario.value = response.usuarioDestino

                _mensaje.value = "Transferencia exitosa"

            } catch (e: Exception) {
                _mensaje.value = "Error: ${e.localizedMessage}"
            }
        }
    }

}
