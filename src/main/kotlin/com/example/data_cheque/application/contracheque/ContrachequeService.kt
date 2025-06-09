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

    fun findAllByContador(contadorId: UUID): List<Contracheque>{
        return contrachequeRepository.findAllByContador(contadorId)
    }

    fun findByIdFuncionario(
        contrachequeId: UUID,
        funcionarioId: UUID
    ): Contracheque?{
        return contrachequeRepository.findByIdFuncionario(contrachequeId, funcionarioId)
    }

    fun findByIdContador(
        contrachequeId: UUID,
        contadorId: UUID
    ): Contracheque?{
        return contrachequeRepository.findByIdContador(contrachequeId, contadorId)
    }

    fun inserir(
        contracheque: ContrachequeCommand,
        contadorId: UUID)
    : Contracheque?{
        val contrachequeDomain = contracheque.toContracheque(contadorId = contadorId)
        contrachequeRepository.inserir(contracheque = contrachequeDomain)
        return findByIdContador(contrachequeDomain.id , contadorId)
    }

    fun atualizar(
        contracheque: ContrachequeCommand,
        contrachequeId: UUID,
        contadorId: UUID
    ): Contracheque?{
        contrachequeRepository.findByIdContador(contrachequeId, contadorId)

        contrachequeRepository.atualizar(
            contracheque.toContracheque( contadorId = contadorId)
                .copy(
                    atualizadoEm = Clock.System.now(),
                    usuarioAtualizacao = "admin",),
            )

        return findByIdContador(contrachequeId = contrachequeId, contadorId = contadorId)
    }

    fun excluir(
        contrachequeId: UUID,
        contadorId: UUID
    ) {
        contrachequeRepository.findByIdContador(contrachequeId = contrachequeId, contadorId = contadorId )
        contrachequeRepository.excluir(contrachequeId)
    }
}