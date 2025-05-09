package com.example.data_cheque.application.funcionario

import com.example.data_cheque.domain.funcionario.Funcionario
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.sql.Timestamp
import java.util.*

@Serializable
data class FuncionarioCreateCommand(
//    val pessoaId: UUID,
//    val usuarioId: UUID,
    val nome: String,
    val cpfcnpj: String,
    val telefone: String,
    val cargo: String,
    val setor: String,
    val salario: Double,
    val dataAdmissao: Timestamp,
    val email: String,
    val senha: String,
)

@Serializable
data class FuncionarioCommandResponse(
    val nome: String,
    val cpfcnpj: String,
    val telefone: String,
    val cargo: String,
    val setor: String,
    val salario: Double,
    val dataAdmissao: Timestamp,
    val email: String,
    val ativo: Boolean
)

fun FuncionarioCreateCommand.toFuncionario(usuarioId: UUID, pessoaId: UUID) = Funcionario(
    id = UUID.randomUUID(),
    usuarioId = usuarioId,
    pessoaId = pessoaId,
    cargo = cargo,
    setor = setor,
    salario = salario,
    dataAdmissao = dataAdmissao
)