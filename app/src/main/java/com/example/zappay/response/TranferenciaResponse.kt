package com.example.zappay.response

import com.example.zappay.request.UsuarioRequest
import com.example.zappay.request.ContactoRequest
import com.google.gson.annotations.SerializedName

data class TransferenciaResponse(
    @SerializedName("status") val status: String,
    @SerializedName("usuario_origen") val usuarioOrigen: UsuarioRequest,
    @SerializedName("usuario_destino") val usuarioDestino: ContactoRequest
)
