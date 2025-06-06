package com.example.data_cheque.application.contracheque

import com.example.data_cheque.domain.contracheque.Contracheque
import kotlinx.datetime.Clock
import java.time.LocalDate
import java.util.*

data class ContrachequeCommand(
    val dataPagamento: LocalDate,
    val dataRefInicio: LocalDate,
    val dataRefFim: LocalDate,
    val salarioBase: Double,
    val horaExtra: Double? = null,
    val adicionalNoturno: Double? = null,
    val comissoes: Double? = null,
    val beneficios: Double? = null,
    val inss: Double,
    val irrf: Double? = null,
    val fgts: Double,
    val outrosDescontos: Double? = null,
)

fun ContrachequeCommand.toContracheque(id: UUID = UUID.randomUUID(), funcId: UUID, contadorId: UUID): Contracheque {
    return Contracheque(
      id= id,
      funcId= funcId,
      contadorId = contadorId,
      dataPagamento= dataPagamento,
      dataRefInicio= dataRefInicio,
      dataRefFim= dataRefFim,
      salarioBase= salarioBase,
      horaExtra= horaExtra,
      adicionalNoturno= adicionalNoturno,
      comissoes= comissoes,
      beneficios= beneficios,
      inss= inss,
      irrf= irrf,
      fgts= fgts,
      outrosDescontos= outrosDescontos,
      criadoEm= Clock.System.now(),
      usuarioCriacao= "admin",
      atualizadoEm= null,
      usuarioAtualizacao= null,
    )
}