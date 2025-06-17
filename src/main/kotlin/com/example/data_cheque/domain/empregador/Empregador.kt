package com.example.data_cheque.domain.empregador

import kotlinx.datetime.Instant
import java.util.UUID

data class Empregador (
    val id: UUID,
    val contadorId: UUID,
    val cnpj: String,
    val razaoSocial: String,
    val nomeFantasia: String,
    val endereco: String,
    val criadoEm: Instant,
    val usuarioCriacao: String,
    val atualizadoEm: Instant?,
    val usuarioAtualizacao: String?
)

