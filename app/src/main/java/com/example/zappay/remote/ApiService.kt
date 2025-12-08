package com.example.zappay.remote

import com.example.zappay.model.Contacto
import com.example.zappay.model.Usuario;
import com.example.zappay.request.ContactoRequest
import com.example.zappay.request.TransferenciaRequest
import com.example.zappay.request.UsuarioRequest
import com.example.zappay.response.TransferenciaResponse
import retrofit2.http.Body
import retrofit2.http.GET;
import retrofit2.http.POST

interface ApiService {
    @GET("https://x8ki-letl-twmt.n7.xano.io/api:i3SbO3sI/registrousuario")
    suspend fun getUsuarios(): List<UsuarioRequest>

    @POST("https://x8ki-letl-twmt.n7.xano.io/api:i3SbO3sI/registrousuario")
    suspend fun crearNuevoUsuario(@Body request: UsuarioRequest): Usuario


    @GET("https://x8ki-letl-twmt.n7.xano.io/api:As3yMSAT/contactos")
    suspend fun getContactos(): List<ContactoRequest>

    @POST("https://x8ki-letl-twmt.n7.xano.io/api:As3yMSAT/contactos")
    suspend fun crearNuevoContacto(@Body request: ContactoRequest): Contacto

    @POST("https://x8ki-letl-twmt.n7.xano.io/api:Lf_wNtyj/transferencia")
    suspend fun transferirFondos(@Body body: TransferenciaRequest): TransferenciaResponse

}





