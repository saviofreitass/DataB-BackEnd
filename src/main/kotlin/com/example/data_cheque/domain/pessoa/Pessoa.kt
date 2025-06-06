package com.example.data_cheque.domain.pessoa

import com.example.data_cheque.domain.usuario.Usuario
import java.util.UUID

data class Pessoa (
    val id: UUID,
//    val usuarioId: UUID
    val nome: String,
    val cpfcnpj: String,
    val telefone: String,
    val ativo: Boolean
)

