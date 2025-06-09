package com.example.data_cheque.application.empregador

import com.example.data_cheque.domain.empregador.Empregador
import com.example.data_cheque.domain.empregador.EmpregadorRepository
import kotlinx.datetime.Clock
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class EmpregadorService(
    private val empregadorRepository: EmpregadorRepository
) {
    fun findAllByContador(contadorId: UUID): List<Empregador> {
        return empregadorRepository.findAllByContador(contadorId)
    }

    fun findByIdContador(contadorId: UUID, empregadorId: UUID): Empregador? {
        return empregadorRepository.findByIdContador(contadorId = contadorId, empregadorId)
    }

    fun inserir(
        empregador: EmpregadorCommand,
        contadorId: UUID,
    ): Empregador?{
        val empregadorDomain = empregador.toEmpregador(contadorId = contadorId)
        empregadorRepository.inserir(empregador = empregadorDomain)
        return findByIdContador(contadorId, empregadorDomain.id)
    }

    fun atualizar(
        empregador: EmpregadorCommand,
        empregadorId: UUID,
        contadorId: UUID
    ): Empregador? {
        empregadorRepository.findByIdContador(contadorId, empregadorId)

        empregadorRepository.atualizar(
            empregador = empregador.toEmpregador(contadorId = contadorId)
                .copy(
                    atualizadoEm = Clock.System.now(),
                    usuarioAtualizacao =  "admin",)
        )
        return findByIdContador(contadorId, empregadorId)
    }

    fun excluir(
        empregadorId: UUID,
        contadorId: UUID
    ){
        empregadorRepository.findByIdContador(contadorId, empregadorId)
        empregadorRepository.excluir(empregadorId)
    }
}