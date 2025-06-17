package com.example.data_cheque.application.contracheque

import com.example.data_cheque.domain.contracheque.Contracheque
import LocalDateSerializer
import com.example.data_cheque.adapters.http.error.UUIDSerializer
import kotlinx.datetime.Clock
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.util.*

@Serializable
data class ContrachequeCommand(
    @Serializable(with = UUIDSerializer::class) val funcId: UUID,
    @Serializable(with = UUIDSerializer::class) val empregadorId: UUID,
    @Serializable(with = LocalDateSerializer::class) val dataPagamento: LocalDate,
    @Serializable(with = LocalDateSerializer::class)val dataRefInicio: LocalDate,
    @Serializable(with = LocalDateSerializer::class)val dataRefFim: LocalDate,
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

fun ContrachequeCommand.toContracheque(id: UUID = UUID.randomUUID(), contadorId: UUID): Contracheque {
    return Contracheque(
      id= id,
      funcId= funcId,
      contadorId = contadorId,
      empregadorId = empregadorId,
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