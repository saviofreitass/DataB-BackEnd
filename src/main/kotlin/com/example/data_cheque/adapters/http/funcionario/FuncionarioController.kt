package com.example.data_cheque.adapters.http.funcionario

import com.example.data_cheque.application.funcionario.FuncionarioCommand
import com.example.data_cheque.application.funcionario.FuncionarioCommandResponse
import com.example.data_cheque.application.pessoa.PessoaService
import com.example.data_cheque.domain.funcionario.Funcionario
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

private const val UUID_REGEX = "[0-9a-fA-F]{8}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{12}"

@RestController
@CrossOrigin(origins = arrayOf("*"))
class FuncionarioController(private val funcionarioHandler: FuncionarioHandler) {
    @GetMapping("/funcionario")
    fun findAll(): ResponseEntity<List<FuncionarioCommandResponse>> {
        return funcionarioHandler.findAll()
    }

    @GetMapping("/funcionario/{funcionarioId:$UUID_REGEX}")
    fun findById(@PathVariable funcionarioId: String) : ResponseEntity<Funcionario> {
        return funcionarioHandler.findById(funcionarioId)
    }

    @PostMapping("/funcionario/cadastro")
    fun insert(@RequestBody funcionario: FuncionarioCommand): ResponseEntity<Funcionario>{
        return funcionarioHandler.insert(funcionario)
    }

    @PutMapping("/funcionario/{funcionarioId:$UUID_REGEX}")
    fun update(@RequestBody funcionario: FuncionarioCommand,
               @PathVariable funcionarioId: String): ResponseEntity<Funcionario> {
        return funcionarioHandler.update(funcionario, funcionarioId)
    }

    @DeleteMapping("/funcionarios/{funcionarioId:$UUID_REGEX}")
    fun delete(@PathVariable funcionarioId: String): ResponseEntity<String> {
        return funcionarioHandler.delete(funcionarioId)
    }
}