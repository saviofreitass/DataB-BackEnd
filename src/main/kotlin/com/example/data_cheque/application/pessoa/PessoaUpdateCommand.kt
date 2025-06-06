package com.example.data_cheque.application.pessoa

import com.example.data_cheque.domain.pessoa.Pessoa
import java.util.UUID

data class PessoaUpdateCommand(
    val id: UUID,
    val nome: String?,
    val cpfcnpj: String?,
    val telefone: String?,
    val ativo: Boolean?
)

fun PessoaUpdateCommand.toPessoaAtualizada(
    pessoa: Pessoa
): Pessoa {
    return pessoa.copy(
        nome = nome ?: pessoa.nome,
        cpfcnpj = cpfcnpj ?: pessoa.cpfcnpj,
        telefone = telefone ?: pessoa.telefone,
        ativo = ativo ?: pessoa.ativo
    )
}

