package com.example.data_cheque.domain.contracheque

import kotlinx.datetime.Instant
import java.time.LocalDate
import java.util.UUID

data class Contracheque (
    val id: UUID,
    val funcId: UUID,
    val contadorId: UUID,
    val dataPagamento: LocalDate,
    val dataRefInicio: LocalDate,
    val dataRefFim: LocalDate,
    val salarioBase: Double,
    val horaExtra: Double?,
    val adicionalNoturno: Double?,
    val comissoes: Double?,
    val beneficios: Double?,
    val inss: Double,
    val irrf: Double?,
    val fgts: Double,
    val outrosDescontos: Double?,
    val criadoEm: Instant,
    val usuarioCriacao: String,
    val atualizadoEm: Instant?,
    val usuarioAtualizacao: String?,
    )

//dataPagamento
//dataRefInicio
//dataRefFim
//salarioBase
//horaExtra
//adicionalNoturno
//comissoes
//beneficios
//inss
//irrf
//fgts
//outrosDescontos
//criadoEm
//usuarioCriacao
//atualizadoEm
//usuarioAtualizacao