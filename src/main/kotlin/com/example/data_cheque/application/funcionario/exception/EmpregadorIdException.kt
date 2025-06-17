package com.example.data_cheque.application.funcionario.exception

sealed class EmpregadorIdException(mensagem: String): Exception(mensagem){
}

class EmpregadorIdInvalido : EmpregadorIdException("Empregador ID inválido!")