package com.example.data_cheque.application.funcionario

import com.example.data_cheque.domain.funcionario.Funcionario
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import java.sql.Timestamp
import java.util.*

data class FuncionarioUpdateCommand(
    val pessoaId: UUID,
    val usuarioId: UUID,
    val nome: String?,
    val cpfcnpj: String?,
    val telefone: String?,
    val cargo: String?,
    val setor: String?,
    val salario: Double?,
    val dataAdmissao: Timestamp?,
    val email: String?,
    val senha: String?,
    val criadoEm: Instant = Clock.System.now(),
    val usuarioCriacao: String?,
    val atualizadoEm: Instant = Clock.System.now(),
    val usuarioAtualizacao: String?,
    val ativo: Boolean?
)

fun FuncionarioUpdateCommand.toFuncionarioAtualizado(
    funcionario: Funcionario
): Funcionario{
    return funcionario.copy(
        cargo = cargo ?: funcionario.cargo,
        setor = setor ?: funcionario.setor,
        salario = salario ?: funcionario.salario,
        dataAdmissao = dataAdmissao ?: funcionario.dataAdmissao
    )
}