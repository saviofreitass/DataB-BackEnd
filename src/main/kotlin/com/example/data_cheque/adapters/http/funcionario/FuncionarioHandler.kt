package com.example.data_cheque.adapters.http.funcionario

import com.example.data_cheque.application.funcionario.FuncionarioCreateCommand
import com.example.data_cheque.application.funcionario.FuncionarioService
import com.example.data_cheque.application.funcionario.FuncionarioUpdateCommand
import com.example.data_cheque.application.funcionario.toFuncionario
import com.example.data_cheque.application.usuario.UsuarioCreateCommand
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

    fun insert(funcionarioCreateCommand: FuncionarioCreateCommand): ResponseEntity<Funcionario>{
        val funcionario = funcionarioService.insert(funcionarioCreateCommand.toFuncionario())
        return ResponseEntity.status(HttpStatus.CREATED).body(funcionario)
    }

    fun update(funcionarioUpdadeCommand: FuncionarioUpdateCommand, funcionarioId: String): ResponseEntity<Funcionario>{
        val funcionario = funcionarioService.update(funcionarioUpdadeCommand, UUID.fromString(funcionarioId))
        return ResponseEntity.ok(funcionario)
    }

    fun delete(funcionarioId: String): ResponseEntity<String> {
        funcionarioService.delete(funcionarioId = UUID.fromString(funcionarioId))
        return ResponseEntity.noContent().build()
    }


}