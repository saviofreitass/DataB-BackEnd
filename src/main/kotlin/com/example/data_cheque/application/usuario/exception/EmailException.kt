package com.example.data_cheque.application.usuario.exception

sealed class EmailException(mensagem: String): Exception(mensagem){
    abstract val email: String?
}

data class EmailExistenteException(
    override val email: String?
): EmailException("Email inválido: email ${email} já existente.")

data class EmailInvalidoException(
    override val email: String?
): EmailException("Email inválido!")

