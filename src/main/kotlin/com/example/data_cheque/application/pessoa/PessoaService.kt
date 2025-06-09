package com.example.data_cheque.application.pessoa

import com.example.data_cheque.application.pessoa.exception.PessoaNaoEncontradaException
import com.example.data_cheque.application.pessoa.strategy.PessoaValidationEstrategy
import com.example.data_cheque.application.pessoa.strategy.impl.CreatePessoa.cpfcnpjValidation
import com.example.data_cheque.application.pessoa.strategy.impl.UpdatePessoa.cpfcnpjUpdateValidation
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

    fun findByCPFCNPJ(cpf: String): Pessoa?{
        return pessoaRepository.findByCPFCNPJ(cpf)
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

    fun createValidation(pessoaCreateCommand: PessoaCreateCommand){
        val validationsStrategies: List<PessoaValidationEstrategy<PessoaCreateCommand>> = listOf(
            cpfcnpjValidation(pessoaRepository)
        )

        validationsStrategies.forEach { it.execute(pessoaCreateCommand) }
    }

    fun updateValidation(pessoaUpdateCommand: PessoaUpdateCommand){
        val validationsStrategies: List<PessoaValidationEstrategy<PessoaUpdateCommand>> = listOf(
            cpfcnpjUpdateValidation(pessoaRepository)
        )
        validationsStrategies.forEach { it.execute(pessoaUpdateCommand) }
    }
}