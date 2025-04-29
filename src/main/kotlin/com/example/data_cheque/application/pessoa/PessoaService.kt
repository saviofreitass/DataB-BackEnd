package com.example.data_cheque.application.pessoa

import com.example.data_cheque.application.pessoa.exception.PessoaNaoEncontradaException
import com.example.data_cheque.domain.pessoa.Pessoa
import com.example.data_cheque.domain.pessoa.PessoaRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class PessoaService(private val pessoaRepository: PessoaRepository){

    fun findAll(): List<Pessoa> {
        return pessoaRepository.findAll()
    }
    fun findById(pessoaId: UUID): Pessoa {
        return pessoaRepository.findById(pessoaId) ?: throw PessoaNaoEncontradaException(pessoaId)
    }


    fun insert(pessoa: PessoaCommand, id: UUID): Pessoa {
        val pessoaDomain = pessoa.toPessoa(id)
        pessoaRepository.insert(pessoaDomain)
        return findById(pessoaDomain.id)
    }

    fun update(pessoa: PessoaCommand): Pessoa {
        pessoaRepository.findById(pessoa.id) ?: throw PessoaNaoEncontradaException(pessoa.id)
        pessoaRepository.update(pessoa.toPessoa(pessoa.id))
        return findById(pessoa.id)
    }

    fun delete(pessoaId: UUID){
        pessoaRepository.findById(pessoaId) ?: throw PessoaNaoEncontradaException(pessoaId)
        pessoaRepository.delete(pessoaId)
    }
}