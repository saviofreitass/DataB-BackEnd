package com.example.data_cheque.adapters.http.pessoa

import com.example.data_cheque.domain.pessoa.Pessoa
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

private const val UUID_REGEX = "[0-9a-fA-F]{8}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{12}"

@RestController
@CrossOrigin(origins = arrayOf("*"))
class PessoaController(
    private val pessoaHandler: PessoaHandler
) {
    @GetMapping("/pessoa")
    fun findAll(): ResponseEntity<List<Pessoa>>{
        return pessoaHandler.findAll()
    }

    @GetMapping("/pessoa/{pessoaId:$UUID_REGEX}")
    fun findById(@PathVariable pessoaId: String): ResponseEntity<Pessoa>{
        return pessoaHandler.findById(pessoaId)
    }
}