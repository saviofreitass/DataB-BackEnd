package com.example.data_cheque.application.funcionario

import com.example.data_cheque.domain.funcionario.Funcionario
import kotlinx.serialization.Serializable
import java.sql.Timestamp
import java.util.*

@Serializable
data class FuncionarioCreateCommand(
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

fun FuncionarioCreateCommand.toFuncionario(pessoaId: UUID, usuarioId: UUID) = Funcionario(
    id = UUID.randomUUID(),
    usuarioId = usuarioId,
    pessoaId = pessoaId,
    cargo = cargo,
    setor = setor,
    salario = salario,
    dataAdmissao = dataAdmissao
)