package com.example.data_cheque.application.contador.exception

import java.util.UUID

sealed class ContadorException(mensagem: String): Exception(mensagem){
    abstract val contadorId: UUID?
}

data class ContadorNaoCadatrado(
    override val contadorId: UUID?
): ContadorException("Erro ao cadastrar o contador")

data class ContadorNaoEncontradoException(
    override val contadorId: UUID?
): ContadorException("Contador $contadorId não encontrado")