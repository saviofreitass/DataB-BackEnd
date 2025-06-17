package com.example.data_cheque.application.funcionario.strategy.impl.createFuncionario

import com.example.data_cheque.application.funcionario.FuncionarioCreateCommand
import com.example.data_cheque.application.funcionario.exception.EmpregadorIdInvalido
import com.example.data_cheque.application.funcionario.strategy.FuncionarioValidationStrategy

class empregadroidValidation(): FuncionarioValidationStrategy<FuncionarioCreateCommand> {
    override fun execute(command: FuncionarioCreateCommand) {
        if (command.empregadorId.isEmpty()) throw EmpregadorIdInvalido()
    }
}