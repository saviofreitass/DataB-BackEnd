package com.example.data_cheque.domain.funcionario

import com.example.data_cheque.domain.pessoa.Pessoa
import com.example.data_cheque.domain.usuario.Usuario
import kotlinx.datetime.Instant
import kotlinx.serialization.Contextual
import java.util.*

data class Funcionario (
    val id: UUID,
    val empregadorId: @Contextual UUID,
    val contadorId: @Contextual UUID,
    val usuario: Usuario,
    val pessoa: Pessoa,
    val cargo: String,
    val setor:  String,
    val salario: Double,
    val dataAdmissao: Instant
)