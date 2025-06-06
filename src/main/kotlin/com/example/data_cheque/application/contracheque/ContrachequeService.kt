package com.example.data_cheque.application.contracheque

import com.example.data_cheque.domain.contracheque.ContraChequeRepository
import com.example.data_cheque.domain.contracheque.Contracheque
import kotlinx.datetime.Clock
import org.springframework.stereotype.Service
import java.util.*

@Service
class ContrachequeService(
    private val contrachequeRepository: ContraChequeRepository
) {
    fun findAllByFuncionario(funcionarioId: UUID): List<Contracheque>{
        return contrachequeRepository.findAllByFuncionario(funcionarioId)
    }

    fun findById(
        contrachequeId: UUID,
        funcionarioId: UUID
    ): Contracheque?{
        return contrachequeRepository.findById(contrachequeId, funcionarioId)
    }

    fun inserir(
        contracheque: ContrachequeCommand,
        funcionarioId: UUID,
        contadorId: UUID)
    : Contracheque?{
        val contrachequeDomain = contracheque.toContracheque(funcId = funcionarioId, contadorId = contadorId)
        contrachequeRepository.inserir(contracheque = contrachequeDomain)
        return findById(contrachequeDomain.id , contrachequeDomain.funcId)
    }

    fun atualizar(
        contracheque: ContrachequeCommand,
        contrachequeId: UUID,
        funcionarioId: UUID,
        contadorId: UUID
    ): Contracheque?{
        contrachequeRepository.findById(contrachequeId, funcionarioId)

        contrachequeRepository.atualizar(
            contracheque.toContracheque(funcId = funcionarioId, contadorId = contadorId)
                .copy(
                    atualizadoEm = Clock.System.now(),
                    usuarioAtualizacao = "admin",
                ),
        )
        return findById(contrachequeId = contrachequeId, funcionarioId = funcionarioId)
    }

    fun excluir(
        contrachequeId: UUID,
        funcionarioId: UUID,
    ) {
        contrachequeRepository.findById(contrachequeId = contrachequeId, funcionarioId = funcionarioId)
        contrachequeRepository.excluir(contrachequeId)
    }
}