package com.example.data_cheque.application.funcionario.exception

import java.util.UUID

sealed class FuncionarioException(mensagem: String): Exception(mensagem){
    abstract val funcionarioId: UUID?
}

data class FuncionarioNaoCadastrado(
    override val funcionarioId: UUID?
): FuncionarioException("Erro ao cadastrar o usuário")

data class FuncionarioNaoEncontradoException(
    override val funcionarioId: UUID?
): FuncionarioException("Funcionário $funcionarioId não encontrado!")