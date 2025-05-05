package com.example.data_cheque.domain.funcionario

import java.sql.Timestamp
import java.util.*

data class Funcionario(
    val id: UUID,
    val usuarioId: UUID,
    val pessoaId: UUID,
    val cargo: String,
    val setor:  String,
    val salario: Double,
    val dataAdmissao: Timestamp
)