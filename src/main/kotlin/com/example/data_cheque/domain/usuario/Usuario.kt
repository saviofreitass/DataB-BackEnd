package com.example.data_cheque.domain.usuario

import kotlinx.datetime.Instant
import java.util.UUID

data class Usuario(
    val id: UUID,
    val email: String,
    val senha: String,
    val tipoUsuario: Role,
    val criadoEm: Instant,
    val usuarioCriacao: String,
    val atualizadoEm: Instant?,
    val usuarioAtualizacao: String?
)
