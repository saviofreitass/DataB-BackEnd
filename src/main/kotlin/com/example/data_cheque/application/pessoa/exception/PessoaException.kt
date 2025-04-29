package com.example.data_cheque.application.pessoa.exception

import java.util.*

sealed class PessoaException(mensagem: String): Exception(mensagem){
    abstract val pessoaId: UUID?
}

data class PessoaNaoEncontradaException(
    override val pessoaId: UUID?
): PessoaException("Pessoa $pessoaId não encontrado!")