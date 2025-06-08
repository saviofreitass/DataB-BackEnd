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

    fun update(pessoaUpdateCommand: PessoaUpdateCommand, id: UUID): Pessoa {
        val pessoaEncontrada = pessoaRepository.findById(id) ?: throw PessoaNaoEncontradaException(id)
        pessoaRepository.update(pessoaUpdateCommand.toPessoaAtualizada(pessoaEncontrada))
        return findById(id)
    }

    fun delete(pessoaId: UUID){
        pessoaRepository.findById(pessoaId) ?: throw PessoaNaoEncontradaException(pessoaId)
        pessoaRepository.delete(pessoaId)
    }
}