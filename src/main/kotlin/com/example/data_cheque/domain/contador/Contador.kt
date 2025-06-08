package com.example.data_cheque.domain.contador

import com.example.data_cheque.domain.pessoa.Pessoa
import com.example.data_cheque.domain.usuario.Usuario
import java.util.*

data class Contador(
    val id: UUID,
    val crc: String,
    val usuario: Usuario,
    val pessoa: Pessoa
)
