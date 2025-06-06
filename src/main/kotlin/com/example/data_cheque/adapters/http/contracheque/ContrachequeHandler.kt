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

    fun findById(contrachequeId: String,
                 funcionarioId: String
    ): ResponseEntity<Contracheque>{
        val contracheque = contrachequeService.findById(UUID.fromString(contrachequeId), UUID.fromString(funcionarioId))
        return ResponseEntity.ok(contracheque)
    }

    fun inserir(
        contrachequeCreateCommand: ContrachequeCommand,
        funcionarioId: String,
        contadorId: String)
    : ResponseEntity<Contracheque>{
        val contracheque = contrachequeService.inserir(contrachequeCreateCommand,
            UUID.fromString(funcionarioId), UUID.fromString(contadorId))
        return ResponseEntity.status(HttpStatus.CREATED).body(contracheque)
    }

    fun atualizar(
        contrachequeUpdateCommand: ContrachequeCommand,
        contrachequeId: String,
        funcionarioId: String,
        contadorId: String
    ): ResponseEntity<Contracheque>{
        val contracheque = contrachequeService.atualizar(contrachequeUpdateCommand, UUID.fromString(contrachequeId),
            UUID.fromString(funcionarioId), UUID.fromString(contadorId))
        return ResponseEntity.status(HttpStatus.OK).body(contracheque)
    }

    fun excluir(
        contrachequeId: String,
        funcionarioId: String,
    ): ResponseEntity<String>{
        contrachequeService.excluir(contrachequeId = UUID.fromString(contrachequeId), funcionarioId = UUID.fromString(funcionarioId))
        return ResponseEntity.noContent().build()
    }
}