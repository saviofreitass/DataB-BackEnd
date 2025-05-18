package com.example.data_cheque.adapters.http.pessoa

import com.example.data_cheque.application.pessoa.PessoaService
import com.example.data_cheque.domain.pessoa.Pessoa
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class PessoaHandler(
    private val pessoaService: PessoaService
) {
    fun findAll(): ResponseEntity<List<Pessoa>>{
        val pessoa = pessoaService.findAll()
        return ResponseEntity.ok(pessoa)
    }

    fun findById(pessoaId: String): ResponseEntity<Pessoa>{
        val pessoa = pessoaService.findById(UUID.fromString(pessoaId))
        return ResponseEntity.ok(pessoa)
    }
}