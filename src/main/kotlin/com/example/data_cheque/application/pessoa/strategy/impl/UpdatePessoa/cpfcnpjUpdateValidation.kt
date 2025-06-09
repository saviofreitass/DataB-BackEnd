package com.example.data_cheque.application.pessoa.strategy.impl.UpdatePessoa

import com.example.data_cheque.application.pessoa.PessoaUpdateCommand
import com.example.data_cheque.application.pessoa.exception.CPFCNPFExistenteException
import com.example.data_cheque.application.pessoa.strategy.PessoaValidationEstrategy
import com.example.data_cheque.domain.pessoa.PessoaRepository

class cpfcnpjUpdateValidation(
    val pessoaRepository: PessoaRepository
): PessoaValidationEstrategy<PessoaUpdateCommand> {
    override fun execute(command: PessoaUpdateCommand) {
        val pessoaEncontrada = command.cpfcnpj?.let { pessoaRepository.findByCPFCNPJ(it) }

        if(pessoaEncontrada?.id != command.id && pessoaEncontrada?.cpfcnpj == command.cpfcnpj){
            throw CPFCNPFExistenteException(pessoaEncontrada?.cpfcnpj)
        }
    }
}