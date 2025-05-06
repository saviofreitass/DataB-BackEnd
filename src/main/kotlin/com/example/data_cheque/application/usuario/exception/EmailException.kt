package com.example.data_cheque.application.usuario.exception

sealed class EmailException(message: String): Exception(message) {
    abstract val email: String
}

data class EmailDuplicadoException(
    override val email: String
): EmailException("Endereço de e-mail já utilizado: $email")

data class EmailInvalidoException(
    override val email: String
): EmailException("Endereço de e-mail inválido: $email")
