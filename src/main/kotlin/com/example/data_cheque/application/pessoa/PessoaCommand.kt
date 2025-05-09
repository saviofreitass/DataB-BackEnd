package com.example.data_cheque.application.pessoa
import com.example.data_cheque.domain.pessoa.Pessoa
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class PessoaCreateCommand (
    val usuarioId: UUID,
    val nome: String,
    val cpfcnpj: String,
    val telefone: String,
    val ativo: Boolean
)

data class PessoaUpdateCommand(
    val id: UUID,
    val usuarioId: UUID,
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
    usuarioId = usuarioId
)

fun PessoaUpdateCommand.toPessoa(id: UUID) = Pessoa(
    id = id,
    usuarioId = usuarioId,
    nome = nome,
    cpfcnpj = cpfcnpj,
    telefone = telefone,
    ativo = ativo,
)




