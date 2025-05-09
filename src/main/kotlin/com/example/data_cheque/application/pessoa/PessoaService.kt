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

    fun insert(pessoaCreateCommand: PessoaCreateCommand): Pessoa {
        val pessoaDomain = pessoaCreateCommand.toPessoa()
        pessoaRepository.insert(pessoaDomain)
        return findById(pessoaDomain.id)
    }

    fun update(pessoaUpdateCommand: PessoaUpdateCommand): Pessoa {
        pessoaRepository.findById(pessoaUpdateCommand.id) ?: throw PessoaNaoEncontradaException(pessoaUpdateCommand.id)
        pessoaRepository.update(pessoaUpdateCommand.toPessoa(pessoaUpdateCommand.id))
        return findById(pessoaUpdateCommand.id)
    }

    fun delete(pessoaId: UUID){
        pessoaRepository.findById(pessoaId) ?: throw PessoaNaoEncontradaException(pessoaId)
        pessoaRepository.delete(pessoaId)
    }
}