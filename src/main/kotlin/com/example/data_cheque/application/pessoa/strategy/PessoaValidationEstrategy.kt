package com.example.data_cheque.application.pessoa.strategy

interface PessoaValidationEstrategy <T> {
    fun execute(command: T)
}