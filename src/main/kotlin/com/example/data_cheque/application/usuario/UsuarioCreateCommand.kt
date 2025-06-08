package com.example.data_cheque.application.usuario

import com.example.data_cheque.domain.usuario.Role
import com.example.data_cheque.domain.usuario.Usuario
import kotlinx.datetime.Clock
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class UsuarioCreateCommand(
    val email: String,
    val senha: String,
    val tipoUsuario: Role,
    val usuarioCriacao: String
)

fun UsuarioCreateCommand.toUsuario(encoderPassword: EncoderPassword) = Usuario(
    id = UUID.randomUUID(),
    email = email,
    senha = encoderPassword.encode(senha),
    tipoUsuario = tipoUsuario,
    usuarioCriacao = usuarioCriacao,
    criadoEm = Clock.System.now(),
    atualizadoEm = null,
    usuarioAtualizacao = null
)

