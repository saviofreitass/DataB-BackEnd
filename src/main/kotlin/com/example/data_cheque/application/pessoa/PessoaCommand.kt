package com.example.data_cheque.application.pessoa

import com.example.data_cheque.domain.pessoa.Pessoa
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class PessoaCommand (
    val id: @Contextual UUID,
    val usuarioId: UUID,
    val nome: String,
    val cpfcnpj: String,
    val telefone: String,
    val ativo: Boolean
)

fun PessoaCommand.toPessoa(id: UUID) = Pessoa(
    id = id,
    nome = nome,
    cpfcnpj = cpfcnpj,
    telefone = telefone,
    ativo = ativo,
    usuarioId = usuarioId
)




