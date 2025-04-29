package com.example.data_cheque.domain.funcionario

import com.example.data_cheque.domain.empregador.Empregador
import kotlinx.datetime.Instant
import java.sql.Timestamp

import java.util.*

data class Funcionario(
    val id: UUID = UUID.randomUUID(),
    val empregador: Empregador,
    val contadorId: UUID,
    val usuarioId: UUID,
    val cargo: String,
    val setor:  String,
    val salario: Double,
    val dataAdmissao: Timestamp,
    val criadoEm: Instant,
    val usuarioCriacao: String,
    val atualizadoEm: Instant?,
    val usuarioAtualizacao: String?
)
