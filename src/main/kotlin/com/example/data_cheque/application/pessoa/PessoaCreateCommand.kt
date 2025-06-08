package com.example.data_cheque.application.pessoa

import com.example.data_cheque.domain.pessoa.Pessoa
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class PessoaCreateCommand (
    val nome: String,
    val cpfcnpj: String,
    val telefone: String,
    val ativo: Boolean
)

fun PessoaCreateCommand.toPessoa() = Pessoa(
    id = UUID.randomUUID(),
    nome = nome,
    cpfcnpj = cpfcnpj,
    telefone = telefone,
    ativo = ativo,
)




