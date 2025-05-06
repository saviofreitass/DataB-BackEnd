package com.example.data_cheque.application.usuario.exception

import java.util.UUID

sealed class UsuarioException(mensagem: String): Exception(mensagem){
    abstract val usuarioId: UUID?
}

data class UsuarioNaoEncontradoException(
    override val usuarioId: UUID?
): UsuarioException("Usuário $usuarioId não encontrado!")

