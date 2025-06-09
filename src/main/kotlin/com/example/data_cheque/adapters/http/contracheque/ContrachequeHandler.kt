package com.example.data_cheque.adapters.http.contracheque

import com.example.data_cheque.application.contracheque.ContrachequeCommand
import com.example.data_cheque.application.contracheque.ContrachequeService
import com.example.data_cheque.domain.contracheque.Contracheque
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class ContrachequeHandler(
    private val contrachequeService: ContrachequeService
) {
    fun findAllByFuncionario(funcionarioId: String): ResponseEntity<List<Contracheque>>{
        val contracheques = contrachequeService.findAllByFuncionario(UUID.fromString(funcionarioId))
        return ResponseEntity.ok(contracheques)
    }

    fun findAllByContador(contadorId: String): ResponseEntity<List<Contracheque>>{
        val contracheques = contrachequeService.findAllByContador(UUID.fromString(contadorId))
        return ResponseEntity.ok(contracheques)
    }

    fun findByIdFuncionario(contrachequeId: String,
                 funcionarioId: String
    ): ResponseEntity<Contracheque>{
        val contracheque = contrachequeService.findByIdFuncionario(UUID.fromString(contrachequeId), UUID.fromString(funcionarioId))
        return ResponseEntity.ok(contracheque)
    }

    fun findByIdContador(contrachequeId: String,
                         contadorId: String
    ):ResponseEntity<Contracheque>{
        val contracheque = contrachequeService.findByIdContador(UUID.fromString(contrachequeId), UUID.fromString(contadorId))
        return ResponseEntity.ok(contracheque)
    }

    fun inserir(
        contrachequeCreateCommand: ContrachequeCommand,
        contadorId: String)
    : ResponseEntity<Contracheque>{
        val contracheque = contrachequeService.inserir(contrachequeCreateCommand, UUID.fromString(contadorId))
        return ResponseEntity.status(HttpStatus.CREATED).body(contracheque)
    }

    fun atualizar(
        contrachequeUpdateCommand: ContrachequeCommand,
        contrachequeId: String,
        contadorId: String
    ): ResponseEntity<Contracheque>{
        val contracheque = contrachequeService.atualizar(contrachequeUpdateCommand, UUID.fromString(contrachequeId),
            UUID.fromString(contadorId))
        return ResponseEntity.status(HttpStatus.OK).body(contracheque)
    }

    fun excluir(
        contrachequeId: String,
        contadorId: String,
    ): ResponseEntity<String>{
        contrachequeService.excluir(contrachequeId = UUID.fromString(contrachequeId), contadorId = UUID.fromString(contadorId))
        return ResponseEntity.noContent().build()
    }
}