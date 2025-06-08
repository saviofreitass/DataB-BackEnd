package com.example.data_cheque.application.usuario

import com.example.data_cheque.domain.usuario.Usuario
import kotlinx.datetime.Clock
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class UsuarioUpdateCommand(
    val id: UUID,
    val email: String?,
    val senha: String?
)

fun UsuarioUpdateCommand.toUsuarioAtualizado(
    usuario: Usuario,
    encoderPassword: EncoderPassword
): Usuario {
    return usuario.copy(
        email = email ?: usuario.email,
        senha = senha?.let { encoderPassword.encode(it) } ?: usuario.senha,
        atualizadoEm = Clock.System.now(),
        usuarioAtualizacao = "adm"
    )
}
