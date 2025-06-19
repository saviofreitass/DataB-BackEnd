package com.example.data_cheque.application.contracheque

import com.example.data_cheque.domain.contracheque.Contracheque
import LocalDateSerializer
import com.example.data_cheque.adapters.http.error.UUIDSerializer
import com.example.data_cheque.domain.contracheque.exception.ValidacaoException
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
    val salarioBase: String,
    val horaExtra: String? = null,
    val adicionalNoturno: String? = null,
    val comissoes: String? = null,
    val beneficios: String? = null,
    val inss: String,
    val irrf: String? = null,
    val fgts: String,
    val outrosDescontos: String? = null,
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
      salarioBase= validarDoubleObrigatorio(salarioBase, "Salario Base"),
      horaExtra= validarDoubleObrigatorio(horaExtra, "Hora extra"),
      adicionalNoturno= validarDoubleObrigatorio(adicionalNoturno, "Adicional noturno"),
      comissoes= validarDoubleObrigatorio(comissoes, "Comissoes"),
      beneficios= validarDoubleObrigatorio(beneficios, "Beneficios"),
      inss= validarDoubleObrigatorio(inss, "Inss"),
      irrf= validarDoubleObrigatorio(irrf, "Irrf"),
      fgts= validarDoubleObrigatorio(fgts, "Fgts"),
      outrosDescontos= validarDoubleObrigatorio(outrosDescontos, "OutrosDescontos"),
      criadoEm= Clock.System.now(),
      usuarioCriacao= "admin",
      atualizadoEm= null,
      usuarioAtualizacao= null,
    )
}

fun validarDoubleObrigatorio(campo: String?, nomeCampo: String): Double {
    if (campo.isNullOrBlank()) {
        // Se for null ou string vazia, retorna 0.0
        return 0.0
    }
    val valor = campo.toDoubleOrNull()
        ?: throw ValidacaoException("Campo '$nomeCampo' inválido: valor deve ser numérico.")
    return valor
}