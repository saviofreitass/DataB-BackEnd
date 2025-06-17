package com.example.data_cheque.application.empregador

import com.example.data_cheque.domain.empregador.Empregador
import kotlinx.datetime.Clock
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class EmpregadorCommand(
    val cnpj: String,
    val razaoSocial: String,
    val nomeFantasia: String,
    val endereco: String,
)

fun EmpregadorCommand.toEmpregador(id: UUID = UUID.randomUUID(), contadorId: UUID): Empregador {
    return Empregador(
        id= id,
        contadorId = contadorId,
        cnpj = cnpj,
        razaoSocial = razaoSocial,
        nomeFantasia = nomeFantasia,
        endereco = endereco,
        criadoEm = Clock.System.now(),
        usuarioCriacao = "admin",
        atualizadoEm = null,
        usuarioAtualizacao = null
    )
}