package com.example.data_cheque.application.usuario

import com.example.data_cheque.domain.usuario.Role
import com.example.data_cheque.domain.usuario.Usuario
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class UsuarioCreatCommand(
    val id: @Contextual UUID,
    val email: String,
    val senha: String,
    val tipoUsuario: Role,
    val atualizadoEm: Instant,
    val usuarioCriacao: String,
    val criadoEm: Instant,
    val usuarioAtualizacao: String
)

fun UsuarioCreatCommand.toUsuario(id: UUID) = Usuario(
    id = id,
    email = email,
    senha = senha,
    tipoUsuario = tipoUsuario,
    atualizadoEm = null,
    usuarioCriacao = "adm",
    criadoEm = Clock.System.now(),
    usuarioAtualizacao = null
)

