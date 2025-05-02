package com.example.data_cheque.adapters.http.ecrypt.exceptions

sealed class CredenciaisException(message: String) : Exception(message)

class CredenciaisInvalidasException() : CredenciaisException("Usuário ou senha invalida")