package com.example.data_cheque.adapters.http.empregador

import com.example.data_cheque.application.empregador.EmpregadorCommand
import com.example.data_cheque.application.empregador.EmpregadorService
import com.example.data_cheque.domain.empregador.Empregador
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import java.util.*

@Component
class EmpregadorHandler(
    private val empregadorService: EmpregadorService
) {

    fun findAllByContador(contadorId: String): ResponseEntity<List<Empregador>> {
        val empregadores = empregadorService.findAllByContador(UUID.fromString(contadorId))
        return ResponseEntity.ok(empregadores)
    }

    fun findByIdContador(contadorId: String,
                         empregadorId: String
    ): ResponseEntity<Empregador> {
        val empregador = empregadorService.findByIdContador(UUID.fromString(contadorId), UUID.fromString(empregadorId.toString()))
        return ResponseEntity.ok(empregador)
    }

    fun inserir(
        empregadorCommand: EmpregadorCommand,
        contadorId: String
    ): ResponseEntity<Empregador> {
        val empregador = empregadorService.inserir(empregadorCommand, UUID.fromString(contadorId))
        return ResponseEntity.status(HttpStatus.CREATED).body(empregador)
    }

    fun atualizar(
        empregadorUpdateCommand: EmpregadorCommand,
        empregadorId: String,
        contadorId: String
    ): ResponseEntity<Empregador> {
        val empregador = empregadorService.atualizar(empregadorUpdateCommand, UUID.fromString(contadorId),
            UUID.fromString(empregadorId) )
        return ResponseEntity.status(HttpStatus.OK).body(empregador)
    }

    fun excluir(
        empregadorId: String,
        contadorId: String
    ): ResponseEntity<String> {
        empregadorService.excluir(UUID.fromString(empregadorId), UUID.fromString(contadorId))
        return ResponseEntity.noContent().build()
    }
}