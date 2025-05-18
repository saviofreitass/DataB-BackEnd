package com.example.data_cheque.adapters.http.funcionario

import com.example.data_cheque.application.funcionario.FuncionarioCreateCommand
import com.example.data_cheque.application.funcionario.FuncionarioUpdateCommand
import com.example.data_cheque.domain.funcionario.Funcionario
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

private const val UUID_REGEX = "[0-9a-fA-F]{8}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{12}"

@RestController
@CrossOrigin(origins = arrayOf("*"))
class FuncionarioController(
    private val funcionarioHandler: FuncionarioHandler
) {
    @GetMapping("/funcionario")
    fun findAll(): ResponseEntity<List<Funcionario>> {
        return funcionarioHandler.findAll()
    }

    @GetMapping("/funcionario/{funcionarioId:$UUID_REGEX}")
    fun findById(@PathVariable funcionarioId: String) : ResponseEntity<Funcionario> {
        return funcionarioHandler.findById(funcionarioId)
    }

    @PostMapping("/funcionario/cadastro")
    fun insert(@RequestBody funcionarioCreateCommand: FuncionarioCreateCommand): ResponseEntity<Funcionario>{
        return funcionarioHandler.insert(funcionarioCreateCommand)
    }

    @PutMapping("/funcionario/{funcionarioId:$UUID_REGEX}")
    fun update(@RequestBody funcionarioUpdateCommand: FuncionarioUpdateCommand,
               @PathVariable funcionarioId: String): ResponseEntity<Funcionario> {
        return funcionarioHandler.update(funcionarioUpdateCommand, funcionarioId)
    }

    @DeleteMapping("/funcionario/{funcionarioId:$UUID_REGEX}")
    fun delete(@PathVariable funcionarioId: String): ResponseEntity<String> {
        return funcionarioHandler.delete(funcionarioId)
    }
}