package com.example.data_cheque.application.pessoa.strategy.impl.CreatePessoa

import com.example.data_cheque.application.pessoa.PessoaCreateCommand
import com.example.data_cheque.application.pessoa.exception.CPFCNPFExistenteException
import com.example.data_cheque.application.pessoa.exception.CPFCNPFInvalidoException
import com.example.data_cheque.application.pessoa.strategy.PessoaValidationEstrategy
import com.example.data_cheque.domain.pessoa.PessoaRepository

class cpfcnpjValidation(
    val pessoaRepository: PessoaRepository
): PessoaValidationEstrategy<PessoaCreateCommand> {
    override fun execute(command: PessoaCreateCommand) {
        val pessoaEncontrada = pessoaRepository.findByCPFCNPJ(command.cpfcnpj)

        if(pessoaEncontrada != null){
            throw CPFCNPFExistenteException(pessoaEncontrada.cpfcnpj)
        }

        if (command.cpfcnpj.isEmpty()){
            throw CPFCNPFInvalidoException(command.cpfcnpj);
        }
    }
}