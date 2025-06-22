package com.example.data_cheque.application.usuario.strategy.Impl.createAccount

import com.example.data_cheque.application.usuario.UsuarioCreateCommand
import com.example.data_cheque.application.usuario.exception.SenhaInvalida
import com.example.data_cheque.application.usuario.strategy.AccountValidationEstrategy

class PasswordValidationImpl: AccountValidationEstrategy<UsuarioCreateCommand> {
    override fun execute(command: UsuarioCreateCommand) {
        val senhaRegex = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$\n")

        if (!senhaRegex.matches(command.senha)){
            throw SenhaInvalida()
        }

    }
}