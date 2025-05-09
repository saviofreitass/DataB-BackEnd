package com.example.data_cheque.application.usuario

import com.example.data_cheque.domain.usuario.Role
import com.example.data_cheque.domain.usuario.Usuario
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class UsuarioCreateCommand(
    val email: String,
    val senha: String,
    val tipoUsuario: Role,
    val atualizadoEm: Instant,
    val usuarioCriacao: String,
    val criadoEm: Instant,
    val usuarioAtualizacao: String
)


@Serializable
data class UsuarioUpdateCommand(
    var id: UUID,
    var email: String,
    var senha: String,
    var tipoUsuario: Role,
    var atualizadoEm: Instant,
    val usuarioCriacao: String,
    val criadoEm: Instant,
    var usuarioAtualizacao: String
)


fun UsuarioCreateCommand.toUsuario(encoderPassword: EncoderPassword) = Usuario(
    id = UUID.randomUUID(),
    email = email,
    senha = encoderPassword.encode(senha),
    tipoUsuario = tipoUsuario,
    atualizadoEm = null,
    usuarioCriacao = "adm",
    criadoEm = Clock.System.now(),
    usuarioAtualizacao = null
)

fun UsuarioUpdateCommand.toUsuario(id: UUID) = Usuario(
    id = id,
    email = email,
    senha = senha,
    tipoUsuario = tipoUsuario,
    atualizadoEm = Clock.System.now(),
    usuarioCriacao = "adm",
    criadoEm = criadoEm,
    usuarioAtualizacao = "adm"
)
