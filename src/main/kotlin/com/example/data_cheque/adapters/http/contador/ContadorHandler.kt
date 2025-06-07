package com.example.data_cheque.adapters.http.contador

import com.example.data_cheque.application.contador.ContadorCreateCommand
import com.example.data_cheque.application.contador.ContadorService
import com.example.data_cheque.application.contador.ContadorUpdateCommand
import com.example.data_cheque.domain.contador.Contador
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class ContadorHandler(
    private val contadorService: ContadorService
) {
    fun findAll(): ResponseEntity<List<Contador>> {
        val contadores = contadorService.findAll()
        return ResponseEntity.ok(contadores)
    }

    fun findById(contadorId: String): ResponseEntity<Contador>{
        val contador = contadorService.findById(UUID.fromString(contadorId))
        return ResponseEntity.ok(contador)
    }

    fun insert(contadorCreateCommand: ContadorCreateCommand): ResponseEntity<Contador> {
        val novoContador = contadorService.insert(contadorCreateCommand)
        return ResponseEntity.status(HttpStatus.CREATED).body(novoContador)
    }

    fun update(
        contadorUpdateCommand: ContadorUpdateCommand,
        contadorId: String
    ): ResponseEntity<Contador> {
        val contadorAtualizado = contadorService.update(contadorUpdateCommand, UUID.fromString(contadorId))
        return ResponseEntity.ok(contadorAtualizado)
    }

    fun delete(contadorId: String): ResponseEntity<String> {
        contadorService.delete(UUID.fromString(contadorId))
        return ResponseEntity.noContent().build()
    }
}