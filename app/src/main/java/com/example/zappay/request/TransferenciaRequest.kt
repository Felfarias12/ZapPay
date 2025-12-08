package com.example.zappay.request

import com.google.gson.annotations.SerializedName

data class TransferenciaRequest(
    @SerializedName("id_origen") val idOrigen: Int,
    @SerializedName("id_destino") val idDestino: Int,
    @SerializedName("monto") val monto: Double
)
