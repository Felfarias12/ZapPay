
package com.example.zappay.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.zappay.model.Contacto
import com.example.zappay.remote.RetrofitInstance
import com.example.zappay.repository.ContactoRepository
import com.example.zappay.request.ContactoRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ContactosViewModel : ViewModel() {

    var contactos by mutableStateOf<List<ContactoRequest>>(emptyList())
        private set

    suspend fun cargarContacto(){
        contactos= withContext(Dispatchers.IO){
            RetrofitInstance.api.getContactos()

        }
    }
}