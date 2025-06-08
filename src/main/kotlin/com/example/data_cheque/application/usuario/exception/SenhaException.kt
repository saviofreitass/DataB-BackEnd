package com.example.data_cheque.application.usuario.exception

sealed class SenhaException(mensagem: String): Exception(mensagem)

class SenhaInvalida: SenhaException("Senha não segue os parâmetros de segurança!")