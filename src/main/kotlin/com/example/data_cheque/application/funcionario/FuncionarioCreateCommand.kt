package com.example.data_cheque.application.funcionario

import com.example.data_cheque.domain.contador.Contador
import com.example.data_cheque.domain.empregador.Empregador
import com.example.data_cheque.domain.funcionario.Funcionario
import com.example.data_cheque.domain.usuario.Usuario
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.sql.Timestamp
import java.util.*

@Serializable
data class FuncionarioCommand(
    val id: @Contextual UUID?,
    val empregador: Empregador,
    val contadorId: UUID,
    val usuarioId: UUID,
    val cargo: String,
    val setor: String,
    val salario: Double,
    val dataAdmissao: Timestamp
)

fun FuncionarioCommand.toFuncionario(id: UUID) = Funcionario(
    id = id,
    empregador = empregador,
    contadorId = contadorId,
    usuarioId = usuarioId,
    cargo = cargo,
    setor = setor,
    salario = salario,
    dataAdmissao = dataAdmissao,
    criadoEm = Clock.System.now(),
    usuarioCriacao = "",
    atualizadoEm = null,
    usuarioAtualizacao = null
)