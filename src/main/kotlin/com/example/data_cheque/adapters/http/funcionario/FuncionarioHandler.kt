package com.example.data_cheque.adapters.http.funcionario

import com.example.data_cheque.application.funcionario.*
import com.example.data_cheque.domain.funcionario.Funcionario
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class FuncionarioHandler(
    private val funcionarioService: FuncionarioService,
) {
    fun findAll(): ResponseEntity<List<Funcionario>> {
        val funcionario = funcionarioService.findAll()
        return ResponseEntity.ok(funcionario)
    }

    fun findById(funcionarioId: String): ResponseEntity<Funcionario> {
        val funcionario = funcionarioService.findById(UUID.fromString(funcionarioId))
        return ResponseEntity.ok(funcionario)
    }

    fun insert(funcionarioCommand: FuncionarioCommand): ResponseEntity<Funcionario>{
        val funcionario = funcionarioService.insert(funcionarioCommand)
        return ResponseEntity.status(HttpStatus.CREATED).body(funcionario)
    }

    fun update(funcionarioCommand: FuncionarioCommand, funcionarioId: String): ResponseEntity<Funcionario>{
        val funcionario = funcionarioService.update(funcionarioCommand, UUID.fromString(funcionarioId))
        return ResponseEntity.ok(funcionario)
    }

    fun delete(funcionarioId: String): ResponseEntity<String> {
        funcionarioService.delete(funcionarioId = UUID.fromString(funcionarioId))
        return ResponseEntity.noContent().build()
    }


}