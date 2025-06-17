package com.example.data_cheque.application.funcionario.strategy

interface FuncionarioValidationStrategy <T>{
    fun execute(command: T)
}