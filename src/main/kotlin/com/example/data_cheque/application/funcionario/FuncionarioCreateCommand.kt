package com.example.data_cheque.application.funcionario

import com.example.data_cheque.domain.funcionario.Funcionario
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.sql.Timestamp
import java.util.*

@Serializable
data class FuncionarioCommand(
    val id: @Contextual UUID?,
//    val contadorId: UUID,
    val pessoaId: UUID = UUID.randomUUID(),
    val usuarioId: UUID = UUID.randomUUID(),
    val nome: String,
    val cpfcnpj: String,
    val telefone: String,
    val cargo: String,
    val setor: String,
    val salario: Double,
    val dataAdmissao: Timestamp,
    val email: String,
    val senha: String,
    val criadoEm: Instant = Clock.System.now(),
    val usuarioCriacao: String,
    val atualizadoEm: Instant = Clock.System.now(),
    val usuarioAtualizacao: String
)

fun FuncionarioCommand.toFuncionario(id: UUID) = Funcionario(
    id = id,
//    contadorId = contadorId,
    usuarioId = usuarioId,
    cargo = cargo,
    setor = setor,
    salario = salario,
    dataAdmissao = dataAdmissao,
    pessoaId = pessoaId
)